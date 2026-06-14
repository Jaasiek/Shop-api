package org.technischools.shop.service;

import org.springframework.stereotype.Service;
import org.technischools.shop.exception.InvalidCategoryException;
import org.technischools.shop.exception.ResourceNotFoundException;
import org.technischools.shop.model.Category;
import org.technischools.shop.model.Product;
import org.technischools.shop.repository.ProductRepository;

import java.util.List;

// singleton (domyślny scope Springa) – serwis bezstanowy
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    private Category parseCategory(String category) {
        try {
            return Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException("Invalid category: " + category);
        }
    }

    @Override
    public List<Product> search(String category, Double maxPrice) {
        if (category != null && maxPrice != null) {
            return productRepository.findByCategoryAndPriceLessThanEqual(
                    parseCategory(category), maxPrice);
        } else if (category != null) {
            return productRepository.findByCategory(parseCategory(category));
        } else if (maxPrice != null) {
            return productRepository.findByPriceLessThanEqual(maxPrice);
        } else {
            return findAll();
        }
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = findById(id);
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setCategory(product.getCategory());
        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Product existing = findById(id);
        productRepository.delete(existing);
    }
}
