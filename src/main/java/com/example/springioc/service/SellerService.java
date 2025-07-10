package com.example.springioc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.SellerDTO;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;
import com.example.springioc.mapper.CategoryMapper;
import com.example.springioc.mapper.ProductMapper;
import com.example.springioc.mapper.SellerMapper;
import com.example.springioc.repository.ProductRepo;
import com.example.springioc.repository.SellerRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
    @Autowired
    private SellerRepo sellerDB;
    @Autowired
    private ProductRepo productDB;

    @Autowired
    private SellerMapper mapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AuthComponents authComponents;

    public SellerDTO CreateSeller(SellerDTO dto) {
        Seller seller = mapper.toEntity(dto);
        Seller saved = sellerDB.save(seller);
        return mapper.toDTO(saved);
    }

    public List<SellerDTO> GetAllSellers() {
        return sellerDB.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public SellerDTO UpdateSeller(Long id, SellerDTO dto) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        Seller currentSeller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));
        Seller targetSeller = sellerDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for ID: " + id));
        if (!targetSeller.getId().equals(currentSeller.getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only update your own seller account");
        }
        MyUser existUser = targetSeller.getUser();
        existUser.setUsername(dto.getUsername());
        List<Product> updatedProducts = dto.getProductsIds().stream().map(productId -> productDB.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for ID: " + productId))).toList();
        targetSeller.getProducts().clear();
        targetSeller.getProducts().addAll(updatedProducts);
        Seller updatedSeller = sellerDB.save(targetSeller);
        return mapper.toDTO(updatedSeller);
    }

    @Transactional
    public void DeleteSeller(Long id) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        System.out.println("User ID: " + userId + ", Seller ID: " + id);
        Seller seller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));
        if (!userId.equals(id) && !isAdmin) {
            throw new EntityNotFoundException("You can only delete your own seller account");
        }
        List<Product> products = productDB.findBySeller(seller);
        for (Product product : products) {
            product.getCustomers().forEach(c -> c.getProducts().remove(product));
            product.getCustomers().clear();
            product.setSeller(null); 
            productDB.save(product); 
        }
        sellerDB.delete(seller);
    }

}
