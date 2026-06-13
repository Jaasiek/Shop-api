package org.technischools.shop.service;

import org.technischools.shop.model.Order;
import org.technischools.shop.model.OrderItemRequest;
import org.technischools.shop.model.OrderRequest;

import java.util.List;

public interface OrderService {

    Order placeOrder(OrderRequest orderRequest);

    Order cancelOrder(Long orderId);

    List<Order> findByCustomer(String customerName);

    // pomocnicze metody dla endpointów GET
    List<Order> findAll();

    Order findById(Long id);
}
