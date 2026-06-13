package org.technischools.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.technischools.shop.model.Product;
import org.technischools.shop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // controller wstrzykuje interfejs, nie implementację
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products – lista produktów (200)
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        // TODO: implementacja
        return ResponseEntity.ok().build();
    }

    // GET /api/products/{id} – szczegóły produktu (200/404)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        // TODO: implementacja
        return ResponseEntity.ok().build();
    }

    // GET /api/products/search?category=&maxPrice= – szukaj (200)
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double maxPrice) {
        // TODO: implementacja
        return ResponseEntity.ok().build();
    }

    // POST /api/products – dodaj produkt (201)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        // TODO: implementacja
        return ResponseEntity.ok().build();
    }

    // PUT /api/products/{id} – edytuj produkt (200/404)
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        // TODO: implementacja
        return ResponseEntity.ok().build();
    }

    // DELETE /api/products/{id} – usuń produkt (204/404)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: implementacja
        return ResponseEntity.noContent().build();
    }
}
