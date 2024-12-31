package org.example.laboratoryappointment.component;

import lombok.RequiredArgsConstructor;
import org.example.laboratoryappointment.interceptor.AdminInterceptor;
import org.example.laboratoryappointment.interceptor.FirstInterceptor;
import org.example.laboratoryappointment.interceptor.ManagerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final FirstInterceptor firstInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final ManagerInterceptor managerInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firstInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login", "/api/user/signin");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**");

        registry.addInterceptor(managerInterceptor)
                .addPathPatterns("/api/manager/**");
    }
}
