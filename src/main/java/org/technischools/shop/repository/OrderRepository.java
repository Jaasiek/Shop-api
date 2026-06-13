package org.technischools.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.technischools.shop.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerNameIgnoreCase(String name);
}
