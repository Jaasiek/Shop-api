package org.technischools.shop.service;

import org.springframework.stereotype.Service;
import org.technischools.shop.model.Product;

import java.util.List;

// singleton (domyślny scope Springa) – serwis bezstanowy
@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> findAll() {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public Product findById(Long id) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public List<Product> search(String category, Double maxPrice) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public Product create(Product product) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public Product update(Long id, Product product) {
        // TODO: implementacja logiki
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO: implementacja logiki
    }
}
