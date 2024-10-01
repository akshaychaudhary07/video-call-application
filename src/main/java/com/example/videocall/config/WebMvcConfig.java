package com.example.videocall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Adjust your mapping
                .allowedOrigins("https://video-call-front-tawny.vercel.app") // Updated to your Vercel frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Added OPTIONS for preflight
                .allowedHeaders("*")
                .allowCredentials(true); // Allow credentials if needed
    }


}
