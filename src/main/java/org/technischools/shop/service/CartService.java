package org.technischools.shop.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.technischools.shop.model.OrderItemRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Koszyk tymczasowy — @Scope("prototype"):
 * Każde wstrzyknięcie daje NOWĄ instancję koszyka.
 * Używamy proxyMode = ScopedProxyMode.TARGET_CLASS, aby w przypadku wstrzyknięcia do singletona,
 * koszyk nie zachowywał się jak singleton.
 * Nie używamy singleton, bo koszyk przechowuje stan (pozycje)
 * specyficzny dla konkretnego żądania/użytkownika.
 * Gdyby był singleton, wszyscy użytkownicy dzieliliby ten sam koszyk.
 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private final List<OrderItemRequest> items = new ArrayList<>();

    public void addItem(OrderItemRequest item) {
        items.add(item);
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public List<OrderItemRequest> getItems() {
        return new ArrayList<>(items);
    }

    public void clear() {
        items.clear();
    }
}
