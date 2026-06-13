package org.technischools.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Klasa żądania: pojedyncza pozycja przy składaniu zamówienia. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {

    private Long productId;

    private int quantity;
}
