package org.technischools.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.technischools.shop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
