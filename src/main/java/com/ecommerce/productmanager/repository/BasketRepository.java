package com.ecommerce.productmanager.repository;

import com.ecommerce.productmanager.entity.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * BasketRepository is a repository interface for managing Basket entities.
 * It extends CrudRepository to provide basic CRUD operations for Basket entities.
 */
@Repository
public interface BasketRepository extends CrudRepository<Basket, String> {
}
