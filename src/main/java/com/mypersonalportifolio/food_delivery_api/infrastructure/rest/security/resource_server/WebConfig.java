package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.accept.PathApiVersionResolver;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String API_BASE_PATH = "/api/v{version}";
    public static final String DEFAULT_API_VERSION = "1";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        var pathVersionResolver = new PathApiVersionResolver(1);
        configurer.useVersionResolver(request -> {
                    String requestPath = request.getRequestURI().substring(request.getContextPath().length());
                    return requestPath.startsWith("/api/") ? pathVersionResolver.resolveVersion(request) : null;
                })
                .setDefaultVersion(DEFAULT_API_VERSION);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_BASE_PATH,
                HandlerTypePredicate.forBasePackage("com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller"));
    }

}
