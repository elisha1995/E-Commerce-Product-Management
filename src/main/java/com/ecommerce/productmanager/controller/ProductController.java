package com.ecommerce.productmanager.controller;

import com.ecommerce.productmanager.model.BrandResponse;
import com.ecommerce.productmanager.model.ProductResponse;
import com.ecommerce.productmanager.model.TypeResponse;
import com.ecommerce.productmanager.service.BrandService;
import com.ecommerce.productmanager.service.ProductService;
import com.ecommerce.productmanager.service.TypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final BrandService brandService;
    private final TypeService typeService;

    public ProductController(ProductService productService, BrandService brandService, TypeService typeService) {
        this.productService = productService;
        this.brandService = brandService;
        this.typeService = typeService;
    }

    /**
     * GET /api/products/{id}
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return ResponseEntity containing the ProductResponse object and HTTP status OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * GET /api/products
     * Retrieves a paginated list of products with optional filtering by brand, type, and keyword.
     *
     * @param page     The page number (default: 0).
     * @param size     The size of each page (default: 10).
     * @param keyword  Optional keyword to filter product names.
     * @param brandId  Optional ID of the brand to filter products.
     * @param typeId   Optional ID of the type to filter products.
     * @param sort     The field to sort by (default: name).
     * @param order    The sort order (asc or desc, default: asc).
     * @return ResponseEntity containing a Page of ProductResponse objects and HTTP status OK.
     */
    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "brandId", required = false) Integer brandId,
            @RequestParam(name = "typeId", required = false) Integer typeId,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        // Convert order to Sort direction
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sorting = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<ProductResponse> productResponses = productService.getProducts(pageable, brandId, typeId, keyword);

        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    /**
     * GET /api/products/brands
     * Retrieves all brands.
     *
     * @return ResponseEntity containing a list of BrandResponse objects and HTTP status OK.
     */
    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }

    /**
     * GET /api/products/types
     * Retrieves all product types.
     *
     * @return ResponseEntity containing a list of TypeResponse objects and HTTP status OK.
     */
    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getTypes() {
        List<TypeResponse> typeResponses = typeService.getAllTypes();
        return new ResponseEntity<>(typeResponses, HttpStatus.OK);
    }
}
