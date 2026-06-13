package org.technischools.shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.technischools.shop.event.OrderCanceledEvent;
import org.technischools.shop.event.OrderPlacedEvent;
import org.technischools.shop.exception.InsufficientStockException;
import org.technischools.shop.exception.ResourceNotFoundException;
import org.technischools.shop.model.*;
import org.technischools.shop.repository.OrderRepository;
import org.technischools.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void should_placeOrder_when_stockIsSufficient() {
        // given
        Product product = Product.builder().id(1L).name("Mouse").price(100).stock(10).build();
        OrderRequest request = OrderRequest.builder()
                .customerName("John Doe")
                .items(List.of(OrderItemRequest.builder().productId(1L).quantity(2).build()))
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(100L);
            return savedOrder;
        });

        // when
        Order order = orderService.placeOrder(request);

        // then
        assertNotNull(order);
        assertEquals("John Doe", order.getCustomerName());
        assertEquals(200, order.getTotalPrice());
        assertEquals(8, product.getStock());

        verify(orderRepository).save(any(Order.class));
        verify(publisher).publishEvent(any(OrderPlacedEvent.class));
    }

    @Test
    void should_throwException_when_stockInsufficient() {
        // given
        Product product = Product.builder().id(1L).name("Mouse").price(100).stock(1).build();
        OrderRequest request = OrderRequest.builder()
                .customerName("John Doe")
                .items(List.of(OrderItemRequest.builder().productId(1L).quantity(5).build()))
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when & then
        assertThrows(InsufficientStockException.class, () -> orderService.placeOrder(request));
        assertEquals(1, product.getStock()); // Stock not changed
        verify(orderRepository, never()).save(any());
        verify(publisher, never()).publishEvent(any());
    }

    @Test
    void should_decreaseStock_when_orderPlaced() {
        // given
        Product product = Product.builder().id(1L).name("Keyboard").price(200).stock(5).build();
        OrderRequest request = OrderRequest.builder()
                .customerName("Jane")
                .items(List.of(OrderItemRequest.builder().productId(1L).quantity(3).build()))
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // when
        orderService.placeOrder(request);

        // then
        assertEquals(2, product.getStock());
    }

    @Test
    void should_restoreStock_when_orderCancelled() {
        // given
        Product product = Product.builder().id(1L).name("Monitor").stock(0).build();
        OrderItem item = OrderItem.builder().product(product).quantity(2).build();
        Order order = Order.builder().id(10L).status(OrderStatus.CONFIRMED).items(List.of(item)).build();

        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));

        // when
        orderService.cancelOrder(10L);

        // then
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals(2, product.getStock());
        verify(publisher).publishEvent(any(OrderCanceledEvent.class));
    }

    @Test
    void should_throwException_when_orderNotFound() {
        // given
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> orderService.cancelOrder(99L));
        verify(publisher, never()).publishEvent(any());
    }
}
