package com.csp.springbootjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
        //.addPathPatterns("/**")
        //.excludePathPatterns("/user/**");
        //.addPathPatterns("/test1")
        //.excludePathPatterns("/login");
        .excludePathPatterns("/**");
    }
}
