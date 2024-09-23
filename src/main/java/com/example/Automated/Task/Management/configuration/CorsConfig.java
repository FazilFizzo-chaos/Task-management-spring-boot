package com.example.Automated.Task.Management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // Allows sending of credentials (cookies, authorization headers)
        config.addAllowedOrigin("http://localhost:5090"); // Specify the allowed origin (frontend URL)
        config.addAllowedHeader("*"); // Allows all headers
        config.addAllowedMethod("*"); // Allows all HTTP methods (GET, POST, etc.)

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}