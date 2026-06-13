package org.technischools.shop.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.technischools.shop.model.Order;
import org.technischools.shop.model.OrderStatus;

import java.time.LocalDate;

@Getter
public class OrderCanceledEvent extends ApplicationEvent {
    private final Order order;
    private final OrderStatus orderStatus;
    private final Double totalPrice;
    private final LocalDate orderDate;
    private final String customerName;

    public OrderCanceledEvent(Object source, Order order) {
        super(source);
        this.order = order;
        this.orderStatus = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.customerName = order.getCustomerName();
    }

    @Override
    public String toString() {
        return "OrderCanceledEvent{" +
                "order=" + order +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                "} has been canceled";
    }
}
