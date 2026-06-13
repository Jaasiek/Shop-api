package org.technischools.shop.service;

public interface OrderService {
        Order placeOrder(String customerName, List<OrderItemRequest> items);
        Order cancelOrder(Long orderId);
        List<Order> findByCustomer(String customerName);
}
