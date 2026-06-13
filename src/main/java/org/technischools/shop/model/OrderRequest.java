package org.technischools.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/** Klasa żądania: złożenie zamówienia (POST /api/orders). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String customerName;

    private List<OrderItemRequest> items;
}
