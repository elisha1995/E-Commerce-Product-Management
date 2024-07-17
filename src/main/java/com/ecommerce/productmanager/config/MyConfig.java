package com.ecommerce.productmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configuration class for setting up Spring Security.
 */
@Configuration
public class MyConfig {

    /**
     * Bean for configuring an in-memory user details service.
     * This service stores user details in memory.
     *
     * @return a UserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Create a user with username "elisha", password "Password", and role "admin"
        UserDetails userDetails = User.builder()
                .username("elisha")
                .password(passwordEncoder().encode("password")) // Encode the password
                .roles("admin")
                .build();

        // Return an InMemoryUserDetailsManager initialized with the user details
        return new InMemoryUserDetailsManager(userDetails);
    }

    /**
     * Bean for configuring the password encoder.
     * This encoder will be used to encode and verify passwords.
     *
     * @return a PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Return a BCryptPasswordEncoder instance
        return new BCryptPasswordEncoder();
    }
}
