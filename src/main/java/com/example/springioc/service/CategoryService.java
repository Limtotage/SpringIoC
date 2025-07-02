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
    private CategoryMapper mapper;
    
    public CategoryDTO CreateCategory(CategoryDTO dto){
        Category category = mapper.toEntity(dto);
        Category saved = categoryDB.save(category);
        return mapper.toDTO(saved);
    }
    
    public List<CategoryDTO> GetAllCategories(){
        return categoryDB.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public Optional<CategoryDTO> GetCategoryByID(Long ID){
        return categoryDB.findById(ID).map(mapper::toDTO);
    } 


    public CategoryDTO UpdateCategory(Long Id, CategoryDTO dto) {
        Category existCategory = categoryDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        existCategory.setDescription(dto.getDescription());
        existCategory.setName(dto.getName());
        List<Product> products = dto.getProductIds().stream().map(productid->productDB.findById(productid)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productid))).collect(Collectors.toList());
        existCategory.setProducts(products);
        Category updated = categoryDB.save(existCategory);
        return mapper.toDTO(updated);
    }

    public void DeleteCategory(Long Id) {
        categoryDB.deleteById(Id);
    }
}
