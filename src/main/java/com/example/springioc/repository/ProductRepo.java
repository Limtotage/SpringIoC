package com.example.springioc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryId(Long categoryId);
}
