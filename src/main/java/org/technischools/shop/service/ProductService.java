package org.technischools.shop.service;

import org.technischools.shop.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    List<Product> search(String category, Double maxPrice);

    Product create(Product product);

    Product update(Long id, Product product);

    void delete(Long id);
}
