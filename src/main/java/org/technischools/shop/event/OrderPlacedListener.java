package org.technischools.shop.event;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.technischools.shop.config.InvoiceNumberGenerator;
import org.technischools.shop.service.EventLogService;

@Component
public class OrderPlacedListener {

    private final EventLogService eventLog;

    // InvoiceNumberGenerator to bean @Scope("prototype") – ObjectProvider daje
    // świeżą instancję przy każdym wywołaniu, zamiast jednej współdzielonej.
    private final ObjectProvider<InvoiceNumberGenerator> invoiceGeneratorProvider;

    public OrderPlacedListener(EventLogService eventLog,
                               ObjectProvider<InvoiceNumberGenerator> invoiceGeneratorProvider) {
        this.eventLog = eventLog;
        this.invoiceGeneratorProvider = invoiceGeneratorProvider;
    }

    @EventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        String invoiceNumber = invoiceGeneratorProvider.getObject().generateInvoiceNumber();
        eventLog.log("OrderPlacedListener",
                "Zamówienie złożone: " + event + " | faktura: " + invoiceNumber);
    }
}
