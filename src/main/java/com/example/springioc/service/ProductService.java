package com.example.springioc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.mapper.ProductMapper;
import com.example.springioc.repository.CategoryRepo;
import com.example.springioc.repository.ProductRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productDB;

    @Autowired
    private CategoryRepo categoryDB; 

    @Autowired
    private ProductMapper mapper; 

    public ProductDTO GetProductByID(Long Id) {
        return productDB.findById(Id).map(mapper::toDTO).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public List<ProductDTO> GetAllProducts(){
        return productDB.findAll().stream().map(mapper::toDTO).toList();
    }
    public ProductDTO CreateProduct(ProductDTO productDTO) {
        Product product = mapper.toEntity(productDTO);
        Product saved = productDB.save(product);
        return mapper.toDTO(saved);
    }
    public ProductDTO UpdateProduct(Long Id, ProductDTO dto) {
        Product existProduct = productDB.findById(Id).orElseThrow(() -> new RuntimeException("Product Not Found"));
        existProduct.setName(dto.getName());
        existProduct.setPrice(dto.getPrice());
        existProduct.setStock(dto.getStock());
        existProduct.setImageUrl(dto.getImageUrl());
        Category category = categoryDB.findById(Id).orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        existProduct.setCategory(category);
        return mapper.toDTO(productDB.save(existProduct));
    }
    public void DeleteProduct(Long Id) {
        productDB.deleteById(Id);
    }
}
