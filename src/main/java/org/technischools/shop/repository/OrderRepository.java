package org.technischools.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.technischools.shop.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findByCustomerNameIgnoreCase(String name);
}
