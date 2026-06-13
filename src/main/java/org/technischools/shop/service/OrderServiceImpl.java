package org.technischools.shop.service;

import org.springframework.stereotype.Service;
import org.technischools.shop.model.Order;
import org.technischools.shop.model.OrderItemRequest;

import java.util.List;

// singleton (domyślny scope Springa) – serwis bezstanowy
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order placeOrder(String customerName, List<OrderItemRequest> items) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public Order cancelOrder(Long orderId) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public List<Order> findByCustomer(String customerName) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public List<Order> findAll() {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public Order findById(Long id) {
        // TODO: implementacja logiki
        return null;
    }
}
