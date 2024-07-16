package com.ecommerce.productmanager.service;

import com.ecommerce.productmanager.entity.Basket;
import com.ecommerce.productmanager.model.BasketResponse;

import java.util.List;

/**
 * BasketService defines the contract for managing Basket entities.
 * It declares methods to retrieve, delete, and create baskets, and to retrieve all baskets.
 */
public interface BasketService {

    /**
     * Retrieves a list of all baskets in the system.
     *
     * @return A list of BasketResponse objects representing all baskets.
     */
    List<BasketResponse> getAllBaskets();

    /**
     * Retrieves a specific basket by its unique identifier.
     *
     * @param basketId The unique identifier of the basket to retrieve.
     * @return A BasketResponse object representing the retrieved basket.
     */
    BasketResponse getBasketById(String basketId);

    /**
     * Deletes a specific basket by its unique identifier.
     *
     * @param basketId The unique identifier of the basket to delete.
     */
    void deleteBasketById(String basketId);

    /**
     * Creates a new basket with the provided details.
     *
     * @param basket The Basket object representing the details of the basket to create.
     * @return A BasketResponse object representing the newly created basket.
     */
    BasketResponse createBasket(Basket basket);
}
