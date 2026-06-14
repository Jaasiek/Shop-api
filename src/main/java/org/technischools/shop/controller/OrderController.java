package org.technischools.shop.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.technischools.shop.model.Order;
import org.technischools.shop.model.OrderRequest;
import org.technischools.shop.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    @GetMapping("/customer/{name}")
    public ResponseEntity<List<Order>> getByCustomer(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findByCustomer(name));
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request.getCustomerName(), request.getItems()));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancel(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(id));
    }
}
