package com.example.springioc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategory(Category category);

    List<Product> findBySeller(Seller seller);

}
