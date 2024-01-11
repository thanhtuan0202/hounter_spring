//package com.hounter.backend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods(HttpMethod.GET.name(),HttpMethod.PUT.name(),HttpMethod.PATCH.name(),
//                        HttpMethod.POST.name(),
//                        HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name())
//                .allowedHeaders(HttpHeaders.CONTENT_TYPE,
//                        HttpHeaders.AUTHORIZATION) // Allowed headers
//                .allowCredentials(false) // Allow sending cookies
//                .maxAge(3600); // Max age of the cache
//    }
//}
//
