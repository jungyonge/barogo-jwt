package app.test.barogojwt.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {

   private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
   public static final String AUTHORIZATION_HEADER = "Authorization";
   private TokenProvider tokenProvider;
   public JwtFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      String jwt = resolveToken(httpServletRequest);
      String requestURI = httpServletRequest.getRequestURI();

      try{
         if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
         } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            servletRequest.setAttribute("exception", JwtValidationMessage.UNKNOWN_JWT_TOKEN.getMessage());
         }
      }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         logger.info("JWT : " + jwt + " 잘못된 JWT 서명입니다.");
         servletRequest.setAttribute("exception", JwtValidationMessage.WRONG_TYPE_JWT_TOKEN.getMessage());
      } catch (ExpiredJwtException e) {
         logger.info("JWT : " + jwt + " 만료된 JWT 토큰입니다.");
         servletRequest.setAttribute("exception", JwtValidationMessage.EXPIRED_JWT_TOKEN.getMessage());
      } catch (UnsupportedJwtException e) {
         logger.info("JWT : " + jwt + " 지원되지 않는 JWT 토큰입니다.");
         servletRequest.setAttribute("exception", JwtValidationMessage.UNSUPPORTED_JWT_TOKEN.getMessage());
      } catch (IllegalArgumentException e) {
         logger.info("JWT : " + jwt + " JWT 토큰이 잘못되었습니다.");
         servletRequest.setAttribute("exception", JwtValidationMessage.WRONG_JWT_TOKEN.getMessage());
      }

      filterChain.doFilter(servletRequest, servletResponse);
   }

   private String resolveToken(HttpServletRequest request) {
      String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }

      return null;
   }
}
