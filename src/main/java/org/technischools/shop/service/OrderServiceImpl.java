package org.technischools.shop.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technischools.shop.event.OrderCanceledEvent;
import org.technischools.shop.event.OrderPlacedEvent;
import org.technischools.shop.exception.InsufficientStockException;
import org.technischools.shop.exception.ResourceNotFoundException;
import org.technischools.shop.model.*;
import org.technischools.shop.repository.OrderRepository;
import org.technischools.shop.repository.ProductRepository;

import java.util.List;

// singleton (domyślny scope Springa) – serwis bezstanowy
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public Order placeOrder(String customerName, List<OrderItemRequest> items) {
        Order order = Order.builder()
                .customerName(customerName)
                .status(OrderStatus.PENDING)
                .build();

        double totalPrice = 0;

        for (OrderItemRequest itemRequest : items) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found: " + itemRequest.getProductId()));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product " + product.getName());
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
            order.getItems().add(orderItem);

            totalPrice += product.getPrice() * itemRequest.getQuantity();
        }

        order.setTotalPrice(totalPrice);
        Order saved = orderRepository.save(order);

        publisher.publishEvent(new OrderPlacedEvent(this, saved));
        return saved;
    }

    @Override
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = findById(orderId);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            return order;
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        publisher.publishEvent(new OrderCanceledEvent(this, order));
        return order;
    }

    @Override
    public List<Order> findByCustomer(String customerName) {
        return orderRepository.findByCustomerNameIgnoreCase(customerName);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id " + id + " Not Found"));
    }
}
