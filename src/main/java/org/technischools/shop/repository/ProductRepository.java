package org.technischools.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.technischools.shop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryAndPriceLessThanEqual(String category, double price);
}
