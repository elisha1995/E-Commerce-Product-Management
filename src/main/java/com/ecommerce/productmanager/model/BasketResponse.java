package com.ecommerce.productmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BasketResponse represents a simplified view of a Basket for response purposes.
 * It contains the ID of the basket and a list of BasketItemResponse objects representing
 * the items in the basket.
 * It uses Lombok annotations to generate boilerplate code such as getters, setters, toString,
 * equals, and hashCode methods.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponse {

    /**
     * The unique identifier for the basket.
     */
    private String id;

    /**
     * A list of BasketItemResponse objects representing the items in the basket.
     */
    private List<BasketItemResponse> items;
}
