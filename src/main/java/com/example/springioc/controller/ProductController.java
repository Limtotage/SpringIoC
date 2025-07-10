package com.example.springioc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.ProductDTO;
import com.example.springioc.repository.SellerRepo;
import com.example.springioc.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SellerRepo sellerDB;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> createProductAdmin(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.CreateProduct(product));
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/seller")
    public ResponseEntity<ProductDTO> createProductSeller(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.CreateProduct(product));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.GetAllProducts());
    }

    @GetMapping("/seller/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<List<ProductDTO>> GetProductBySeller(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductsBySeller(id));
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<List<ProductDTO>> GetProductByCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(productService.GetProductsByCustomer(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.UpdateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.DeleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}