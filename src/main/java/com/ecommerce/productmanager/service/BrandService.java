package com.ecommerce.productmanager.service;

import com.ecommerce.productmanager.model.BrandResponse;

import java.util.List;

/**
 * BrandService defines the contract for managing Brand entities.
 * It declares a method to retrieve all brands.
 */
public interface BrandService {

    /**
     * Retrieves a list of all brands in the system.
     *
     * @return A list of BrandResponse objects representing all brands.
     */
    List<BrandResponse> getAllBrands();
}
