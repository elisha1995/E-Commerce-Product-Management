package com.ecommerce.productmanager.config;

import com.ecommerce.productmanager.security.JwtAuthenticationEntryPoint;
import com.ecommerce.productmanager.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security settings.
 * This class configures the security filter chain, JWT authentication, and session management.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAuthenticationFilter filter;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Constructor to initialize SecurityConfig with JwtAuthenticationEntryPoint and JwtAuthenticationFilter.
     *
     * @param entryPoint handles unauthorized access attempts
     * @param filter processes JWT tokens in incoming requests
     */
    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint, JwtAuthenticationFilter filter) {
        this.entryPoint = entryPoint;
        this.filter = filter;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection
        http.csrf(csrf -> csrf.disable())
                // Configure URL-based authorization
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/products").authenticated() // Require authentication for /products
                        .requestMatchers("/auth/login").permitAll() // Allow all access to /auth/login
                        .requestMatchers("/actuator/**").permitAll() // Permit all access to Actuator endpoints (or customize as needed)
                        .anyRequest().permitAll()) // Allow all access to other requests
                // Configure exception handling to use JwtAuthenticationEntryPoint for unauthorized requests
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                // Configure session management to be stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides an AuthenticationManager bean.
     *
     * @param http the HttpSecurity object to configure
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return authenticationManagerBuilder.getObject();
    }
}
