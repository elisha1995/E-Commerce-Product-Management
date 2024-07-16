package com.ecommerce.productmanager.exceptions;

import com.ecommerce.productmanager.model.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * CustomExceptionHandler is a global exception handler for handling specific exceptions
 * across the whole application. It is annotated with @ControllerAdvice to be detected
 * and registered as a bean by Spring.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ProductNotFoundException and returns a custom error response with HTTP status 404.
     *
     * @param ex the exception that was thrown
     * @param request the current request
     * @return a ResponseEntity containing the CustomErrorResponse and HTTP status 404
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        // Create a custom error response with the appropriate HTTP status and error details
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                HttpStatus.NOT_FOUND,    // HTTP status code
                "Product doesn't exist", // Brief error description
                ex.getMessage()          // Detailed error message from the exception
        );
        // Return the custom error response with HTTP status 404
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
}
