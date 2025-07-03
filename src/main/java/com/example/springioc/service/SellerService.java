package com.example.springioc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springioc.dto.SellerDTO;
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
        Seller existingSeller = sellerDB.findById(id).orElse(null);
        if (existingSeller == null) {
            return null;
        }
        existingSeller.setProducts(dto.getProducts().stream()
                .map(product -> productMapper.toEntity(product))
                .collect(Collectors.toList()));
        Seller updatedSeller = sellerDB.save(existingSeller);
        return mapper.toDTO(updatedSeller);
    }

    @Transactional
    public void DeleteSeller(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        Seller seller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));
        if (!seller.getId().equals(id)) {
            throw new EntityNotFoundException("You can only delete your own seller account");
        }
        else {
            sellerDB.delete(seller);
        }
    }
}
