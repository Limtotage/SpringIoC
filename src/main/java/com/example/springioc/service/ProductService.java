package com.example.springioc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;
import com.example.springioc.mapper.ProductMapper;
import com.example.springioc.repository.CategoryRepo;
import com.example.springioc.repository.ProductRepo;
import com.example.springioc.repository.SellerRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productDB;

    @Autowired
    private CategoryRepo categoryDB; 

    @Autowired
    private ProductMapper mapper; 
    @Autowired
    private SellerRepo sellerDB;

    public ProductDTO GetProductByID(Long Id) {
        return productDB.findById(Id).map(mapper::toDTO).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public List<ProductDTO> GetAllProducts(){
        return productDB.findAll().stream().map(mapper::toDTO).toList();
    }
    public ProductDTO CreateProduct(ProductDTO productDTO) {
        Product product = mapper.toEntity(productDTO);
        Category category = categoryDB.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        Seller seller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));
        product.setSeller(seller);
        product.setCategory(category);
        Product saved = productDB.save(product);
        return mapper.toDTO(saved);
    }
    public ProductDTO UpdateProduct(Long Id, ProductDTO dto) {
        Product existProduct = productDB.findById(Id).orElseThrow(() -> new RuntimeException("Product Not Found"));
        existProduct.setName(dto.getName());
        existProduct.setPrice(dto.getPrice());
        existProduct.setStock(dto.getStock());
        Category category = categoryDB.findById(dto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        existProduct.setCategory(category);
        return mapper.toDTO(productDB.save(existProduct));
    }
    public void DeleteProduct(Long Id) {
        productDB.deleteById(Id);
    }
}
