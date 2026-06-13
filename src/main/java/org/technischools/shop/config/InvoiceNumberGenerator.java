package org.technischools.shop.config;


import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceNumberGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public String generateInvoiceNumber() {
        int year = LocalDate.now().getYear();
        int nextNumber = counter.incrementAndGet();

        return String.format("FV/%d/%03d", year, nextNumber);
    }
}