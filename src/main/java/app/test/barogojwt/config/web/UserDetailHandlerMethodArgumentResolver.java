package app.test.barogojwt.config.web;

import app.test.barogojwt.config.security.CustomUserDetails;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserDetailHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CustomUserDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (object instanceof CustomUserDetails) {
                return object;
            }
        }

        // anonymousUser일 경우 null로 리턴
        return null;
    }
}
