package com.security.spring_security.controller;

import com.security.spring_security.entity.Product;
import com.security.spring_security.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();

        if (products != null && !products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.notFound().build();
    }

    //@PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productRepository.save(product)
        );
    }

    @PreAuthorize("hasAuthority('EDIT_ONE_PRODUCT')")
    @PutMapping
    public ResponseEntity<Product> editOne(@RequestBody @Valid Product product) {
        Product product1 = productRepository.findById(product.getId()).orElse(null);

        if (product1 != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    productRepository.save(product)
            );
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('DELETE_ONE_PRODUCT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            productRepository.delete(product);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/one/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e, HttpServletRequest request) {
        Map<String, String> apiError = new HashMap<>();
        apiError.put("message", e.getLocalizedMessage());
        apiError.put("timestamp", new Date().toString());
        apiError.put("url", request.getRequestURL().toString());
        apiError.put("http-method", request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }
        return ResponseEntity.status(status).body(apiError);
    }
}
