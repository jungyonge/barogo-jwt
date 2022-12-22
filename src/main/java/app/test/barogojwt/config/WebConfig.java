package app.test.barogojwt.config;

import app.test.barogojwt.config.web.UserDetailHandlerMethodArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final UserDetailHandlerMethodArgumentResolver userDetailHandlerMethodArgumentResolver;

    public WebConfig(
            UserDetailHandlerMethodArgumentResolver userDetailHandlerMethodArgumentResolver) {
        this.userDetailHandlerMethodArgumentResolver = userDetailHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userDetailHandlerMethodArgumentResolver);
    }
}
