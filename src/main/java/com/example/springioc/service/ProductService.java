package com.example.springioc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Product;
import com.example.springioc.repository.ProductRepo;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productDB;

    public List<Product> GetAllProducts(){
        return productDB.findAll();
    }

    public List<Product> SearchProducts(String search){
        return productDB.findByNameContainingIgnoreCase(search);
    }
}
