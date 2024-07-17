package com.ecommerce.productmanager.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Custom implementation of {@link AuthenticationEntryPoint} to handle
 * unauthorized access attempts by returning a 401 Unauthorized response.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is called whenever an exception is thrown due to an unauthorized
     * user trying to access a protected resource. It sends a 401 Unauthorized response
     * along with a custom message.
     *
     * @param request the {@link HttpServletRequest} being processed
     * @param response the {@link HttpServletResponse} to be sent to the client
     * @param authException the exception that caused the invocation
     * @throws IOException if an input or output exception occurs
     * @throws ServletException if a servlet exception occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Set the response status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Get the response writer to write the error message
        PrintWriter writer = response.getWriter();

        // Write the error message to the response
        writer.println("Access Denied: " + authException.getMessage());
    }
}
