package org.technischools.shop.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.technischools.shop.model.Order;
import org.technischools.shop.model.OrderStatus;

import java.time.LocalDate;

@Getter
public class OrderPlacedEvent extends ApplicationEvent {

    private final Order order;
    private final OrderStatus orderStatus;
    private final Double totalPrice;
    private final LocalDate orderDate;
    private final String customerName;

    public OrderPlacedEvent(Object source, Order order) {
        super(source);
        this.order = order;
        this.orderStatus = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.customerName = order.getCustomerName();
    }

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "order=" + order +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
