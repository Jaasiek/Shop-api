package org.technischools.shop.controller;

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
    // controller wstrzykuje interfejs, nie implementację
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /api/orders – lista zamówień (200)
    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    // GET /api/orders/{id} – szczegóły zamówienia (200/404)
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    // GET /api/orders/customer/{name} – zamówienia klienta (200)
    @GetMapping("/customer/{name}")
    public ResponseEntity<List<Order>> getByCustomer(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findByCustomer(name));
    }

    // POST /api/orders – złóż zamówienie (201/409)
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    // PUT /api/orders/{id}/cancel – anuluj zamówienie (200/404)
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancel(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(id));
    }
}
