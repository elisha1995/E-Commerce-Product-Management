package com.ecommerce.productmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a JWT request.
 * This class is used to encapsulate the username and password provided by the user for authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    // The username of the user
    public String username;

    // The password of the user.
    public String password;
}
