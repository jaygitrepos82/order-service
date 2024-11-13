package com.eshop.order_service.entity;

import com.eshop.order_service.dto.Customer;
import com.eshop.order_service.dto.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"ORDER\"") // Use escaped quotes to treat ORDER as a table name
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDateTime orderDate;
    private int quantity;
    private BigDecimal totalPrice;
    private String status;

    private Long productId;
    private Long customerId;

    @Transient
    private Product productDetails;

    @Transient
    private Customer customerDetails;
    // Getters and Setters
}
