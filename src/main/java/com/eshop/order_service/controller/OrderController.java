package com.eshop.order_service.controller;

import com.eshop.order_service.dto.Customer;
import com.eshop.order_service.dto.Product;
import com.eshop.order_service.entity.Order;
import com.eshop.order_service.exception.ResourceNotFoundException;
import com.eshop.order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
       // Optional<Order> order = orderService.getOrderById(orderId);

        Order order = orderService.getOrderById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        logger.info("In Controller: getOrder = " + orderId);
        logger.info("In Controller: getOrder: getTotalPrice = " + order.getTotalPrice());
        try {
            // Fetch product and customer details using product_id and customer_id
            Product product = restTemplate.getForObject("http://localhost:8080/api/products/" + order.getProductId(), Product.class);
            Customer customer = restTemplate.getForObject("http://localhost:9000/api/customers/" + order.getCustomerId(), Customer.class);

            logger.info("In Controller: product name       : = " + product.getName());
            logger.info("In Controller: customer first name: = " + customer.getFirstName());
            // Add product and customer details to the order response
            order.setProductDetails(product);
            order.setCustomerDetails(customer);
        }
        catch (HttpClientErrorException.NotFound e) {
            // Handle the 404 case
            System.out.println("Endpoint not found: " + e.getMessage());
        }
        return ResponseEntity.ok(order);
    }

}