package com.ecommerce.productmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * CustomErrorResponse is a model class that represents the structure of error responses
 * to be sent to the client when an error occurs in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    // The HTTP status code representing the type of error (e.g., 404, 500)
    private HttpStatus status;

    // A brief error code or description (e.g., "Not Found", "Internal Server Error")
    private String error;

    // A detailed message providing more information about the error
    private String message;
}
