package com.ecom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(GET, POST, PUT, DELETE,"OPTIONS")
                        .allowedHeaders("*")
                        .allowedOrigins("http://localhost:4200", "https://68077231d29e719b2b8700ed--stupendous-marshmallow-35895f.netlify.app","https://silver-heliotrope-fbb646.netlify.app", "https://clickshop-backend.onrender.com/")
                        .allowCredentials(true);
            }
        };
    }
}
