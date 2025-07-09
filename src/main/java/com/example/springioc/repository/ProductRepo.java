package com.example.springioc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findBySeller(Seller seller);

    @Query("SELECT p FROM Product p JOIN p.customers c WHERE c = :customer")
    List<Product> findByCustomer(@Param("customer") Customer customer);
}
