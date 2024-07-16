package com.ecommerce.productmanager.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

/**
 * Basket represents a shopping basket containing multiple basket items.
 * This class is annotated with @RedisHash to indicate that it is a Redis hash entity.
 * It uses Lombok's @Data annotation to generate boilerplate code such as getters, setters, toString, equals, and hashCode methods.
 * It also uses Lombok's @NoArgsConstructor to generate a no-argument constructor.
 */
@Data
@NoArgsConstructor
@RedisHash("Basket")
public class Basket {

    /**
     * The unique identifier for the basket.
     * This field is annotated with @Id to mark it as the primary key.
     */
    @Id
    private String id;

    /**
     * A list of BasketItem objects representing the items in the basket.
     * Initialized to an empty ArrayList to avoid null pointer exceptions.
     */
    private List<BasketItem> items = new ArrayList<>();

    /**
     * Constructs a new Basket with the specified ID.
     *
     * @param id the unique identifier for the basket
     */
    public Basket(String id) {
        this.id = id;
    }
}
