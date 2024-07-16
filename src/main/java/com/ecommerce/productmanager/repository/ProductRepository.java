package com.ecommerce.productmanager.repository;

import com.ecommerce.productmanager.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository is a repository interface for managing Product entities.
 * It extends JpaRepository to provide standard CRUD operations and adds custom query methods.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Finds all products that match the given specification and returns them in a paginated format.
     *
     * @param spec the specification to filter the products
     * @param pageable the pagination information
     * @return a paginated list of products matching the specification
     */
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    /**
     * Creates a specification to find products with names containing the given keyword.
     *
     * @param keyword the keyword to search for in product names
     * @return a specification for searching products by name
     */
    Specification<Product> searchByNameContaining(String keyword);

    /**
     * Creates a specification to find products by the given brand ID.
     *
     * @param brandId the ID of the brand to filter by
     * @return a specification for filtering products by brand ID
     */
    Specification<Product> findByBrandId(Integer brandId);

    /**
     * Creates a specification to find products by the given type ID.
     *
     * @param typeId the ID of the type to filter by
     * @return a specification for filtering products by type ID
     */
    Specification<Product> findByTypeId(Integer typeId);

    /**
     * Creates a specification to find products by the given brand ID and type ID.
     *
     * @param brandId the ID of the brand to filter by
     * @param typeId the ID of the type to filter by
     * @return a specification for filtering products by both brand ID and type ID
     */
    Specification<Product> findByBrandIdAndTypeId(Integer brandId, Integer typeId);
}
