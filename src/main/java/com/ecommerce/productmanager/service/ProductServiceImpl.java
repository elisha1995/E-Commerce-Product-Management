package com.ecommerce.productmanager.service;

import com.ecommerce.productmanager.entity.Product;
import com.ecommerce.productmanager.exceptions.ProductNotFoundException;
import com.ecommerce.productmanager.model.ProductResponse;
import com.ecommerce.productmanager.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * ProductServiceImpl is a service class that implements the ProductService interface.
 * It provides methods for fetching products by their ID and for fetching paginated lists
 * of products with optional filtering by brand, type, and keyword.
 */
@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor to inject the ProductRepository dependency
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetches a product by its ID and converts it to a ProductResponse.
     *
     * @param productId the ID of the product to fetch
     * @return the ProductResponse for the fetched product
     * @throws ProductNotFoundException if the product with the given ID does not exist
     */
    @Override
    public ProductResponse getProductById(Integer productId) {
        log.info("Fetching Product by Id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));

        // Convert the Product entity to ProductResponse
        ProductResponse productResponse = convertToProductResponse(product);
        log.info("Fetched Product by Product Id: {}", productId);
        return productResponse;
    }

    /**
     * Fetches a paginated list of products with optional filtering by brand ID, type ID, and keyword.
     *
     * @param pageable the pagination information
     * @param brandId the ID of the brand to filter by (optional)
     * @param typeId the ID of the type to filter by (optional)
     * @param keyword the keyword to filter by (optional)
     * @return a paginated list of ProductResponse objects
     */
    @Override
    public Page<ProductResponse> getProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword) {
        log.info("Fetching all products");
        Specification<Product> spec = Specification.where(null);

        // Add brand filter if brandId is provided
        if (brandId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("brand").get("id"), brandId));
        }

        // Add type filter if typeId is provided
        if (typeId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("type").get("id"), typeId));
        }

        // Add keyword filter if keyword is provided
        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
        }
        log.info("Fetched all products");

        // Fetch products from the repository and convert them to ProductResponse objects
        return productRepository.findAll(spec, pageable).map(this::convertToProductResponse);
    }

    /**
     * Converts a Product entity to a ProductResponse object.
     *
     * @param product the Product entity to convert
     * @return the converted ProductResponse object
     */
    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productBrand(product.getBrand().getName())
                .productType(product.getType().getName())
                .build();
    }
}
