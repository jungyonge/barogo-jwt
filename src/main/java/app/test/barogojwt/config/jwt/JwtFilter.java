package app.test.barogojwt.config.jwt;

import app.test.barogojwt.api.authorization.response.TokenDto;
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
   private final TokenProvider tokenProvider;
   public JwtFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      String accessToken = resolveToken(httpServletRequest);
      String requestURI = httpServletRequest.getRequestURI();

      try{
         if (StringUtils.hasText(accessToken) && tokenProvider.validateAccessToken(accessToken)) {
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
         } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            servletRequest.setAttribute("exception", JwtValidationMessage.UNKNOWN_JWT_TOKEN.getMessage());
         }
      }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         servletRequest.setAttribute("exception", JwtValidationMessage.WRONG_TYPE_JWT_TOKEN.getMessage());
      } catch (ExpiredJwtException e) {
         servletRequest.setAttribute("exception", JwtValidationMessage.EXPIRED_JWT_TOKEN.getMessage());
      } catch (UnsupportedJwtException e) {
         servletRequest.setAttribute("exception", JwtValidationMessage.UNSUPPORTED_JWT_TOKEN.getMessage());
      } catch (IllegalArgumentException e) {
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
