package com.ecommerce.productmanager.service;

import com.ecommerce.productmanager.entity.Brand;
import com.ecommerce.productmanager.model.BrandResponse;
import com.ecommerce.productmanager.repository.BrandRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BrandServiceImpl is the implementation of BrandService interface.
 * It provides methods to fetch and manage Brand entities.
 */
@Service
@Log4j2
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Retrieves all brands from the database.
     *
     * @return A list of BrandResponse objects representing all brands.
     */
    @Override
    public List<BrandResponse> getAllBrands() {
        log.info("Fetching All Brands!!!");

        // Fetch all brands from repository
        List<Brand> brandList = brandRepository.findAll();

        // Convert Brand entities to BrandResponse DTOs using streams
        List<BrandResponse> brandResponses = brandList.stream()
                .map(this::convertToBrandResponse)
                .collect(Collectors.toList());

        log.info("Fetched All Brands!!!");
        return brandResponses;
    }

    /**
     * Converts a Brand entity to a BrandResponse DTO.
     *
     * @param brand The Brand entity to convert.
     * @return A BrandResponse DTO representing the Brand entity.
     */
    private BrandResponse convertToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
