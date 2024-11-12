package com.eshop.order_service.service;

import com.eshop.order_service.entity.Order;
import com.eshop.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        System.out.println("In createOrder... " + order);
//        order.setOrderDate(LocalDateTime.now());
//        order.setTotalPrice(order.getQuantity().multiply(order.getPrice()));
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll().stream()
//         .filter(order -> order.getOrderId() > 0)
//                .collect(java.util.stream.Collectors.toList());
//    }


    // Additional CRUD operations can be implemented here
}