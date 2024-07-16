package com.ecommerce.productmanager.exceptions;

/**
 * ProductNotFoundException is a custom exception class that is thrown when a requested product
 * cannot be found in the database. It extends the RuntimeException class.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a new ProductNotFoundException with the specified detail message.
     *
     * @param message the detail message, which provides more information about the reason for the exception
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
