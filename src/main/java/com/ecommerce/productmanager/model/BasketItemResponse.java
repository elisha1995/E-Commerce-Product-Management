package com.ecommerce.productmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BasketItemResponse represents a simplified view of a BasketItem for response purposes.
 * It uses Lombok annotations to generate boilerplate code such as getters, setters, toString,
 * equals, and hashCode methods.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemResponse {

    /**
     * The unique identifier for the basket item.
     */
    private Integer id;

    /**
     * The name of the product associated with the basket item.
     */
    private String name;

    /**
     * A description of the product associated with the basket item.
     */
    private String description;

    /**
     * The price of the product associated with the basket item.
     */
    private Long price;

    /**
     * The URL of the picture associated with the product.
     */
    private String pictureUrl;

    /**
     * The brand of the product associated with the basket item.
     */
    private String productBrand;

    /**
     * The type or category of the product associated with the basket item.
     */
    private String productType;

    /**
     * The quantity of the product associated with the basket item.
     */
    private Integer quantity;
}
