package com.ecommerce.productmanager.service;

import com.ecommerce.productmanager.entity.Basket;
import com.ecommerce.productmanager.entity.BasketItem;
import com.ecommerce.productmanager.model.BasketItemResponse;
import com.ecommerce.productmanager.model.BasketResponse;
import com.ecommerce.productmanager.repository.BasketRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BasketServiceImpl is the implementation of the BasketService interface.
 * It provides methods to interact with Basket entities and convert them to response DTOs.
 */
@Service
@Log4j2
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    /**
     * Retrieves a list of all baskets in the system.
     *
     * @return A list of BasketResponse objects representing all baskets.
     */
    @Override
    public List<BasketResponse> getAllBaskets() {
        log.info("Fetching All Baskets");
        List<Basket> basketList = (List<Basket>) basketRepository.findAll();
        // Mapping baskets to response DTOs
        List<BasketResponse> basketResponses = basketList.stream()
                .map(this::convertToBasketResponse)
                .collect(Collectors.toList());
        log.info("Fetched all Baskets");
        return basketResponses;
    }

    /**
     * Retrieves a specific basket by its unique identifier.
     *
     * @param basketId The unique identifier of the basket to retrieve.
     * @return A BasketResponse object representing the retrieved basket, or null if not found.
     */
    @Override
    public BasketResponse getBasketById(String basketId) {
        log.info("Fetching Basket by Id: {}", basketId);
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            log.info("Fetched Basket by Id: {}", basketId);
            return convertToBasketResponse(basket);
        } else {
            log.info("Basket with Id: {} not found", basketId);
            return null;
        }
    }

    /**
     * Deletes a specific basket by its unique identifier.
     *
     * @param basketId The unique identifier of the basket to delete.
     */
    @Override
    public void deleteBasketById(String basketId) {
        log.info("Deleting Basket by Id: {}", basketId);
        basketRepository.deleteById(basketId);
        log.info("Deleted Basket by Id: {}", basketId);
    }

    /**
     * Creates a new basket with the provided details.
     *
     * @param basket The Basket object representing the details of the basket to create.
     * @return A BasketResponse object representing the newly created basket.
     */
    @Override
    public BasketResponse createBasket(Basket basket) {
        log.info("Creating Basket");
        Basket savedBasket = basketRepository.save(basket);
        log.info("Basket created with Id: {}", savedBasket.getId());
        return convertToBasketResponse(savedBasket);
    }

    /**
     * Converts a Basket entity to a BasketResponse DTO.
     *
     * @param basket The Basket entity to convert.
     * @return A BasketResponse DTO representing the converted basket.
     */
    private BasketResponse convertToBasketResponse(Basket basket) {
        if (basket == null) {
            return null;
        }
        List<BasketItemResponse> itemResponses = basket.getItems().stream()
                .map(this::convertToBasketItemResponse)
                .collect(Collectors.toList());
        return BasketResponse.builder()
                .id(basket.getId())
                .items(itemResponses)
                .build();
    }

    /**
     * Converts a BasketItem entity to a BasketItemResponse DTO.
     *
     * @param basketItem The BasketItem entity to convert.
     * @return A BasketItemResponse DTO representing the converted basket item.
     */
    private BasketItemResponse convertToBasketItemResponse(BasketItem basketItem) {
        return BasketItemResponse.builder()
                .id(basketItem.getId())
                .name(basketItem.getName())
                .description(basketItem.getDescription())
                .price(basketItem.getPrice())
                .pictureUrl(basketItem.getPictureUrl())
                .productBrand(basketItem.getProductBrand())
                .productType(basketItem.getProductType())
                .quantity(basketItem.getQuantity())
                .build();
    }
}
