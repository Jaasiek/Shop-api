package org.technischools.shop.event;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.technischools.shop.service.EventLogService;

@Component
public class OrderCanceledListener {
    private final EventLogService eventLog;

    public OrderCanceledListener(EventLogService eventLog) {
        this.eventLog = eventLog;
    }

    @EventListener
    public void onOrderCanceled(OrderCanceledEvent event) {
        eventLog.log("OrderCanceledListener", "Zamówienie anulowane: " + event);
    }
}
