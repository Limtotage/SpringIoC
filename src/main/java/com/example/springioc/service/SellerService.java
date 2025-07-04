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
import com.example.springioc.repository.SellerRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
    @Autowired
    private SellerRepo sellerDB;

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
        Seller ownerSeller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));
        Seller targetSeller = sellerDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for ID: " + id));
        if (!targetSeller.getId().equals(ownerSeller.getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only update your own seller account");
        } else {
            MyUser existUser = targetSeller.getUser();
            existUser.setUsername(dto.getUsername());
            List<Product> updatedProducts = dto.getProducts().stream()
                    .map(productMapper::toEntity)
                    .collect(Collectors.toList());
            targetSeller.getProducts().clear();
            targetSeller.getProducts().addAll(updatedProducts);
            Seller updatedSeller = sellerDB.save(targetSeller);
            return mapper.toDTO(updatedSeller);
        }
    }

    @Transactional
    public void DeleteSeller(Long id) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        Seller seller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));
        if (!seller.getId().equals(id) && !isAdmin) {
            throw new EntityNotFoundException("You can only delete your own seller account");
        } else {
            sellerDB.delete(seller);
        }
    }
}
