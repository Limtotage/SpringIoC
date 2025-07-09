package com.example.springioc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.mapper.CategoryMapper;
import com.example.springioc.mapper.ProductMapper;
import com.example.springioc.repository.CategoryRepo;
import com.example.springioc.repository.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepo categoryDB;
    @Autowired
    private ProductRepo productDB;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper mapper;

    public CategoryDTO CreateCategory(CategoryDTO dto, Boolean isApproved) {
        Category category = mapper.toEntity(dto);
        category.setIsApproved(isApproved);
        Category saved = categoryDB.save(category);
        return mapper.toDTO(saved);
    }

    public List<CategoryDTO> GetAllCategories() {
        return categoryDB.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<CategoryDTO> GetAllApprovedCategories() {
        return categoryDB.findByIsApprovedTrue().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<CategoryDTO> GetAllUnapprovedCategories() {
        return categoryDB.findAll().stream()
                .filter(category -> !category.getIsApproved())
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> GetCategoryByID(Long ID) {
        return categoryDB.findById(ID).map(mapper::toDTO);
    }

    public CategoryDTO UpdateCategory(Long Id, CategoryDTO dto) {
        Category existCategory = categoryDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));

        existCategory.setDescription(dto.getDescription());
        existCategory.setName(dto.getName());
        List<Product> updatedProducts = dto.getProductsIds().stream()
                .map(productIds -> productDB.findById(productIds).orElseThrow(() -> new EntityNotFoundException("Product Not Found with ID: " + productIds)))
                .toList();
        existCategory.getProducts().clear();
        existCategory.getProducts().addAll(updatedProducts);
        Category updated = categoryDB.save(existCategory);
        return mapper.toDTO(updated);
    }

    public CategoryDTO UpdateCategoryApproval(Long Id, Boolean isApproved) {
        Category existCategory = categoryDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        existCategory.setIsApproved(isApproved);
        Category updated = categoryDB.save(existCategory);
        return mapper.toDTO(updated);
    }

    public void DeleteCategory(Long Id) {
        categoryDB.deleteById(Id);
    }
}
