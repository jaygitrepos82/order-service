package com.eshop.order_service.service;

import com.eshop.order_service.entity.Order;
import com.eshop.order_service.exception.ResourceNotFoundException;
import com.eshop.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        logger.info("In createOrder: Order Id = " + order.getOrderId());
//        order.setOrderDate(LocalDateTime.now());
//        order.setTotalPrice(order.getQuantity().multiply(order.getPrice()));
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        logger.info("In getAllOrders: count = " + orderRepository.findAll().stream().count());
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        logger.info("In getOrderById: getting order details for  = " + orderId);
        return Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId)));
    }

//    public List<Order> getAllOrders() {
//        return orderRepository.findAll().stream()
//         .filter(order -> order.getOrderId() > 0)
//                .collect(java.util.stream.Collectors.toList());
//    }


    // Additional CRUD operations can be implemented here
}