package com.example.springioc.service;

import org.springframework.stereotype.Service;

import com.example.springioc.repository.StockRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepo stockDB;
    
    public int getStockQuantity(Long productId) {
        return stockDB.findByProduct_Id(productId)
                .map(stock -> stock.getStockQuantity())
                .orElseThrow(() -> new IllegalArgumentException("Stock not found for product ID: " + productId));
    }

}
