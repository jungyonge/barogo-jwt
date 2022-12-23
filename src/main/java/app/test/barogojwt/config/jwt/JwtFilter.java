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
      TokenDto tokenDto = resolveToken(httpServletRequest);
      String requestURI = httpServletRequest.getRequestURI();

      try{
         if (tokenDto != null && tokenProvider.validateAccessToken(tokenDto)) {
            Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccess_token());
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

   private TokenDto resolveToken(HttpServletRequest request) {
      String access_token = request.getHeader("access_token");
      String refresh_token = request.getHeader("refresh_token");

      if(access_token != null && refresh_token != null){
         return new TokenDto(access_token, refresh_token);
      }

      return null;
   }
}
