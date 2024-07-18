package com.ecommerce.productmanager.service;

import com.ecommerce.productmanager.entity.OrderAggregate.Order;
import com.ecommerce.productmanager.entity.OrderAggregate.OrderItem;
import com.ecommerce.productmanager.entity.OrderAggregate.ProductItemOrdered;
import com.ecommerce.productmanager.mapper.OrderMapper;
import com.ecommerce.productmanager.model.BasketItemResponse;
import com.ecommerce.productmanager.model.BasketResponse;
import com.ecommerce.productmanager.model.OrderDto;
import com.ecommerce.productmanager.model.OrderResponse;
import com.ecommerce.productmanager.repository.BrandRepository;
import com.ecommerce.productmanager.repository.OrderRepository;
import com.ecommerce.productmanager.repository.TypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Marks this class as a Spring service component
@Log4j2 // Enables logging using Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BrandRepository brandRepository;
    private final TypeRepository typeRepository;
    private final BasketService basketService;
    private final OrderMapper orderMapper;

    // Constructor injection for dependencies
    public OrderServiceImpl(OrderRepository orderRepository, BrandRepository brandRepository, TypeRepository typeRepository, BasketService basketService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.brandRepository = brandRepository;
        this.typeRepository = typeRepository;
        this.basketService = basketService;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse getOrderById(Integer orderId) {
        // Fetch order by ID and map it to OrderResponse if present
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(orderMapper::OrderToOrderResponse).orElse(null);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        // Fetch all orders and map them to OrderResponse
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::OrderToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        // Fetch all orders with pagination and map them to OrderResponse
        return orderRepository.findAll(pageable).map(orderMapper::OrderToOrderResponse);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        // Delete order by ID
        orderRepository.deleteById(orderId);
    }

    @Override
    public Integer createOrder(OrderDto orderDto) {
        // Fetch basket details by ID
        BasketResponse basketResponse = basketService.getBasketById(orderDto.getBasketId());
        if (basketResponse == null) {
            log.error("Basket with ID {} not found", orderDto.getBasketId());
            return null;
        }

        // Map basket items to order items
        List<OrderItem> orderItems = basketResponse.getItems().stream()
                .map(this::mapBasketItemToOrderItem)
                .collect(Collectors.toList());

        // Calculate subtotal
        double subTotal = basketResponse.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Set order details
        Order order = orderMapper.orderResponseToOrder(orderDto);
        order.setOrderItems(orderItems);
        order.setSubTotal(subTotal);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Delete the basket by ID
        basketService.deleteBasketById(orderDto.getBasketId());

        // Return the order ID
        return savedOrder.getId();
    }

    // Maps a BasketItemResponse to an OrderItem
    private OrderItem mapBasketItemToOrderItem(BasketItemResponse basketItemResponse) {
        if (basketItemResponse != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemOrdered(mapBasketItemToProduct(basketItemResponse));
            orderItem.setQuantity(basketItemResponse.getQuantity());
            return orderItem;
        } else {
            return null;
        }
    }

    // Maps a BasketItemResponse to a ProductItemOrdered
    private ProductItemOrdered mapBasketItemToProduct(BasketItemResponse basketItemResponse) {
        ProductItemOrdered productItemOrdered = new ProductItemOrdered();
        // Populate ProductItemOrdered with BasketItemResponse data
        productItemOrdered.setName(basketItemResponse.getName());
        productItemOrdered.setPictureUrl(basketItemResponse.getPictureUrl());
        productItemOrdered.setProductId(basketItemResponse.getId());
        return productItemOrdered;
    }
}
