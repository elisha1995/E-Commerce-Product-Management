package com.ecommerce.productmanager.controller;

import com.ecommerce.productmanager.entity.Basket;
import com.ecommerce.productmanager.entity.BasketItem;
import com.ecommerce.productmanager.model.BasketItemResponse;
import com.ecommerce.productmanager.model.BasketResponse;
import com.ecommerce.productmanager.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    /**
     * GET /api/baskets
     * Retrieves all baskets.
     *
     * @return A list of BasketResponse objects representing all baskets.
     */
    @GetMapping
    public List<BasketResponse> getAllBaskets() {
        return basketService.getAllBaskets();
    }

    /**
     * GET /api/baskets/{basketId}
     * Retrieves a specific basket by its ID.
     *
     * @param basketId The ID of the basket to retrieve.
     * @return The BasketResponse object representing the requested basket.
     */
    @GetMapping("/{basketId}")
    public BasketResponse getBasketById(@PathVariable String basketId){
        return basketService.getBasketById(basketId);
    }

    /**
     * DELETE /api/baskets/{basketId}
     * Deletes a specific basket by its ID.
     *
     * @param basketId The ID of the basket to delete.
     */
    @DeleteMapping("/{basketId}")
    public void deleteBasketById(@PathVariable String basketId){
        basketService.deleteBasketById(basketId);
    }

    /**
     * POST /api/baskets
     * Creates a new basket based on the provided BasketResponse object.
     *
     * @param basketResponse The BasketResponse object containing basket information.
     * @return ResponseEntity containing the created BasketResponse and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<BasketResponse> createBasket(@RequestBody BasketResponse basketResponse){
        // Convert BasketResponse to Basket entity
        Basket basket = convertToBasketEntity(basketResponse);
        // Call service method to create the basket
        BasketResponse createdBasket = basketService.createBasket(basket);
        return new ResponseEntity<>(createdBasket, HttpStatus.CREATED);
    }

    /**
     * Converts a BasketResponse object to a Basket entity.
     *
     * @param basketResponse The BasketResponse object to convert.
     * @return The Basket entity converted from BasketResponse.
     */
    private Basket convertToBasketEntity(BasketResponse basketResponse) {
        Basket basket = new Basket();
        basket.setId(basketResponse.getId());
        basket.setItems(mapBasketItemResponsesToEntities(basketResponse.getItems()));
        return basket;
    }

    /**
     * Maps a list of BasketItemResponse objects to a list of BasketItem entities.
     *
     * @param itemResponses The list of BasketItemResponse objects to map.
     * @return The list of BasketItem entities mapped from BasketItemResponse objects.
     */
    private List<BasketItem> mapBasketItemResponsesToEntities(List<BasketItemResponse> itemResponses) {
        return itemResponses.stream()
                .map(this::convertToBasketItemEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converts a BasketItemResponse object to a BasketItem entity.
     *
     * @param itemResponse The BasketItemResponse object to convert.
     * @return The BasketItem entity converted from BasketItemResponse.
     */
    private BasketItem convertToBasketItemEntity(BasketItemResponse itemResponse) {
        BasketItem basketItem = new BasketItem();
        basketItem.setId(itemResponse.getId());
        basketItem.setName(itemResponse.getName());
        basketItem.setDescription(itemResponse.getDescription());
        basketItem.setPrice(itemResponse.getPrice());
        basketItem.setPictureUrl(itemResponse.getPictureUrl());
        basketItem.setProductBrand(itemResponse.getProductBrand());
        basketItem.setProductType(itemResponse.getProductType());
        basketItem.setQuantity(itemResponse.getQuantity());
        return basketItem;
    }
}
