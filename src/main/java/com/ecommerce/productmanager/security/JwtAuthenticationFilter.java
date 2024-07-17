package com.ecommerce.productmanager.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to handle JWT authentication for incoming requests.
 * This filter intercepts each request and checks for a valid JWT token in the Authorization header.
 * If a valid token is found, the user is authenticated and the request proceeds.
 */
@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor to initialize JwtAuthenticationFilter with JwtHelper and UserDetailsService.
     *
     * @param jwtHelper utility class to work with JWT tokens
     * @param userDetailsService service to load user-specific data
     */
    public JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters incoming requests to authenticate JWT tokens.
     *
     * @param request the HttpServletRequest being processed
     * @param response the HttpServletResponse being generated
     * @param filterChain the FilterChain used to invoke the next filter
     * @throws ServletException if an input or output exception occurs
     * @throws IOException if a servlet-specific error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get the Authorization header from the request
        String requestHeader = request.getHeader("Authorization");
        log.info("Header: {}", requestHeader);

        String userName = null;
        String token = null;

        // Check if the Authorization header contains a Bearer token
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7); // Extract the token from the header
            try {
                // Extract the username from the token
                userName = this.jwtHelper.getUserNameFromToken(token);
            } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {
                log.info("Jwt Token Processing Error");
                e.printStackTrace();
            }
        } else {
            log.warn("JWT token doesn't start with Bearer String");
        }

        // Validate the token and set the authentication if the token is valid
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user details using the username extracted from the token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken) {
                // Create an authentication token and set it in the security context
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                log.info("Token is not valid");
            }
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
