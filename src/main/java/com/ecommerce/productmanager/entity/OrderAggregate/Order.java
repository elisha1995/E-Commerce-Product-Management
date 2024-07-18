package com.ecommerce.productmanager.entity.OrderAggregate;

import jakarta.persistence.*; // Importing JPA annotations
import lombok.AllArgsConstructor; // Lombok annotation for all-args constructor
import lombok.Builder; // Lombok annotation for builder pattern
import lombok.Data; // Lombok annotation for getters, setters, toString, equals, and hashCode methods
import lombok.NoArgsConstructor; // Lombok annotation for no-args constructor

import java.time.LocalDateTime; // Importing LocalDateTime for order date
import java.util.List; // Importing List for order items

@Entity // Specifies that the class is an entity and is mapped to a database table
@Table(name = "Orders") // Specifies the table name in the database
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@Builder // Lombok annotation to implement the builder pattern
public class Order {

    @Id // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the generation strategy for the primary key
    @Column(name = "Id") // Specifies the column name in the table
    private Integer id;

    @Column(name = "Basket_Id") // Specifies the column name in the table
    private String basketId;

    @Embedded // Specifies that this is an embedded field
    private ShippingAddress shippingAddress;

    @Column(name = "Order_Date") // Specifies the column name in the table
    private LocalDateTime orderDate = LocalDateTime.now(); // Sets the default value to the current date and time

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order") // Specifies a one-to-many relationship with OrderItem
    private List<OrderItem> orderItems;

    @Column(name = "Sub_Total") // Specifies the column name in the table
    private Double subTotal;

    @Column(name = "Delivery_Fee") // Specifies the column name in the table
    private Long deliveryFee;

    @Enumerated(EnumType.STRING) // Specifies that the enum should be persisted as a string
    @Column(name = "Order_Status") // Specifies the column name in the table
    private OrderStatus orderStatus = OrderStatus.Pending; // Sets the default order status to Pending

    // Method to calculate the total order cost
    public Double getTotal() {
        return getSubTotal() + getDeliveryFee();
    }
}
