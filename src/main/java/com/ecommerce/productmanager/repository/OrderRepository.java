package com.ecommerce.productmanager.repository;

import com.ecommerce.productmanager.entity.OrderAggregate.Order;
import com.ecommerce.productmanager.entity.OrderAggregate.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // Marks this interface as a Spring Data repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Finds orders by basket ID.
     *
     * @param basketId the basket ID
     * @return a list of orders associated with the specified basket ID
     */
    List<Order> findByBasketId(String basketId);

    /**
     * Finds orders by order status.
     *
     * @param orderStatus the status of the orders
     * @return a list of orders with the specified status
     */
    List<Order> findByOrderStatus(OrderStatus orderStatus);

    /**
     * Finds orders placed within a specified date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of orders placed within the specified date range
     */
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Finds orders that contain a product with a specified name.
     *@Query Defines custom JPQL queries for more complex database operations.
     * @param productName the name of the product
     * @return a list of orders containing the specified product name
     */
    @Query("SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.itemOrdered.name LIKE %:productName%")
    List<Order> findByProductNameInOrderItems(@Param("productName") String productName);

    /**
     * Finds orders shipped to a specified city.
     *
     * @param city the city to which the orders are shipped
     * @return a list of orders shipped to the specified city
     */
    @Query("SELECT o FROM Order o WHERE o.shippingAddress.city = :city")
    List<Order> findByShippingAddressCity(@Param("city") String city);
}
