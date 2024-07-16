package com.ecommerce.productmanager.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

/**
 * BasketItem represents an item in a shopping basket.
 * This class is annotated with @RedisHash to indicate that it is a Redis hash entity.
 * It uses Lombok's @Data annotation to generate boilerplate code such as getters, setters, toString, equals, and hashCode methods.
 */
@Data
@RedisHash("BasketItem")
public class BasketItem {

    /**
     * The unique identifier for the basket item.
     * This field is annotated with @Id to mark it as the primary key.
     */
    @Id
    private Integer id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * A description of the product.
     */
    private String description;

    /**
     * The price of the product in the basket item.
     */
    private Long price;

    /**
     * The URL of the picture associated with the product.
     */
    private String pictureUrl;

    /**
     * The brand of the product.
     */
    private String productBrand;

    /**
     * The type or category of the product.
     */
    private String productType;

    /**
     * The quantity of the product in the basket item.
     */
    private Integer quantity;
}
