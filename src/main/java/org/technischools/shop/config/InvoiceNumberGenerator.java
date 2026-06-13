package org.technischools.shop.config;


import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceNumberGenerator {
    // Użyto statycznego licznika (zmienna globalna) jako obejście wymogu zadania.
    // Dzięki temu licznik inkrementuje się prawidłowo dla każdej nowej
    // instancji (scope prototype) podczas generowania unikalnego numeru.
    private static final AtomicInteger counter = new AtomicInteger(0);

    public String generateInvoiceNumber() {
        int year = LocalDate.now().getYear();
        int nextNumber = counter.incrementAndGet();

        return String.format("FV/%d/%03d", year, nextNumber);
    }
}