package app.test.barogojwt.config.jwt;

import app.test.barogojwt.api.authorization.response.TokenDto;
import app.test.barogojwt.config.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements InitializingBean {

   private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
   private static final String AUTHORITIES_KEY = "auth";
   private final String access_token_secret;
   private final String refresh_token_secret;

   private final long accessTokenValidityInMilliseconds;
   private final long refreshTokenValidityInMilliseconds;
   private Key access_key;
   private Key refresh_key;


   private final UserDetailsService userDetailsService;


   public TokenProvider(
           @Value("${jwt.access-token-secret}") String access_token_secret,
           @Value("${jwt.refresh-token-secret}") String refresh_token_secret,
           @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
           @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds,
           UserDetailsService userDetailsService) {
      this.access_token_secret = access_token_secret;
      this.refresh_token_secret = refresh_token_secret;
      this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
      this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
      this.userDetailsService = userDetailsService;
   }

   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(access_token_secret);
      this.access_key = Keys.hmacShaKeyFor(keyBytes);
      keyBytes = Decoders.BASE64.decode(refresh_token_secret);
      this.refresh_key = Keys.hmacShaKeyFor(keyBytes);
   }

   public String createAccessToken(String username, String authorities) {
      long now = (new Date()).getTime();
      Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

      return Jwts.builder()
         .setSubject(username)
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(access_key, SignatureAlgorithm.HS512)
         .setExpiration(validity)
         .compact();
   }

   public String createRefreshToken(String username, String authorities) {

      long now = (new Date()).getTime();
      Date validity = new Date(now + this.refreshTokenValidityInMilliseconds);

      return Jwts.builder()
              .setSubject(username)
              .claim(AUTHORITIES_KEY, authorities)
              .signWith(refresh_key, SignatureAlgorithm.HS512)
              .setExpiration(validity)
              .compact();
   }


   public Authentication getAuthentication(String token) {
      Claims claims = Jwts
              .parserBuilder()
              .setSigningKey(access_key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      Collection<? extends GrantedAuthority> authorities =
         Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

      User principal = new User(claims.getSubject(), "", authorities);

      CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(
              principal.getUsername());

      return new UsernamePasswordAuthenticationToken(customUserDetails, token, authorities);
   }

   public boolean validateAccessToken(TokenDto tokenDto) {
      try {
         Jwts.parserBuilder().setSigningKey(access_key).build().parseClaimsJws(tokenDto.getAccess_token());
         return true;
      }catch (Exception e) {
         logger.info("JWT : " + tokenDto.getAccess_token() + "  JWT 검증 실패.");
         throw e;
      }
   }

   public Jws<Claims> validateRefreshToken(String refreshToken) {
      try {
         return Jwts.parserBuilder().setSigningKey(refresh_key).build().parseClaimsJws(refreshToken);
      }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         logger.info("JWT : " + refreshToken + " 잘못된 JWT 서명입니다.");
         throw e;
      } catch (ExpiredJwtException e) {
         logger.info("JWT : " + refreshToken + " 만료된 JWT 토큰입니다.");
         throw e;
      } catch (UnsupportedJwtException e) {
         logger.info("JWT : " + refreshToken + " 지원되지 않는 JWT 토큰입니다.");
         throw e;
      } catch (IllegalArgumentException e) {
         logger.info("JWT : " + refreshToken + " JWT 토큰이 잘못되었습니다.");
         throw e;
      }
   }

   public String regenerationAccessToken(String accessToken, String refreshToken){

      Jws<Claims> claims = validateRefreshToken(refreshToken);
      return createAccessToken(claims.getBody().get("sub").toString(),
              String.valueOf(claims.getBody().get("roles")));

   }
}
