package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Seller;

public interface SellerRepo extends JpaRepository<Seller, Long> {
    
}
