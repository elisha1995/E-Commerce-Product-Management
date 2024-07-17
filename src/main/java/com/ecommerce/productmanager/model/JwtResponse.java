package com.ecommerce.productmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a JWT response.
 * This class is used to encapsulate the username and JWT token provided to the user upon successful authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    // The username of the authenticated user.
    private String username;

    // The JWT token assigned to the authenticated user.
    private String token;
}
