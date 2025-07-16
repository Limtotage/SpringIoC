package com.example.springioc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Stock;

public interface StockRepo extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduct_Id(Long productId);
} 
    
