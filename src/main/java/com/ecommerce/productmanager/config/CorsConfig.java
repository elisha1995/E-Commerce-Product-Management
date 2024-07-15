package com.ecommerce.productmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up Cross-Origin Resource Sharing (CORS) settings
 * for the application.
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings to allow cross-origin requests from any origin
     * to all endpoints in the application, with the allowed HTTP methods being
     * GET, POST, DELETE, and PUT.
     *
     * @param registry the CORS registry to add mappings to
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow cross-origin requests to any endpoint in the application
        registry.addMapping("/**")
                // Allow requests from any origin
                .allowedOrigins("*")
                // Allow specified HTTP methods
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // Allow all headers in the requests
                .allowedHeaders("*");
    }
}
