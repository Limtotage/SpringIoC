package com.example.springioc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.mapper.CategoryMapper;
import com.example.springioc.repository.CategoryRepo;
import com.example.springioc.repository.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryDB;
    private final ProductRepo productDB;
    private final CategoryMapper mapper;

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

        existCategory.setName(dto.getName());
        List<Product> updatedProducts = dto.getProductsIds().stream()
                .map(productIds -> productDB.findById(productIds)
                        .orElseThrow(() -> new EntityNotFoundException("Product Not Found with ID: " + productIds)))
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

    public Category ensureUncategorizedCategoryExists() {
        return categoryDB.findByName("Uncategorized")
                .orElseGet(() -> {
                    Category uncategorized = new Category();
                    uncategorized.setName("Uncategorized");
                    uncategorized.setIsApproved(true); 
                    return categoryDB.save(uncategorized);
                });
    }

    public void DeleteCategory(Long Id) {
        Category categoryToDelete = categoryDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        if (categoryToDelete.getName().equals("Uncategorized")) {
            throw new IllegalArgumentException("Cannot delete the Uncategorized category");
        }
        List<Product> productsWithCategory = productDB.findByCategory(categoryToDelete);

        if (!productsWithCategory.isEmpty()) {
            Category uncategorized = ensureUncategorizedCategoryExists();
            for (Product product : productsWithCategory) {
                product.setCategory(uncategorized);
            }
            productDB.saveAll(productsWithCategory);
        }
        categoryDB.deleteById(Id);
    }
}
