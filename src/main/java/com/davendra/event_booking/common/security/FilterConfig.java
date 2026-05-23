package com.davendra.event_booking.common.security;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<FirebaseAuthenticationFilter> firebaseFilter(
            FirebaseAuthenticationFilter filter
    ) {

        FilterRegistrationBean<FirebaseAuthenticationFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(filter);

        registration.addUrlPatterns("/api/*");

        return registration;
    }
}
