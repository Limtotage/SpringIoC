package com.example.springioc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.entity.Category;
import com.example.springioc.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> GetCategoryByID(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.GetCategoryByID(id));
    }
    @GetMapping
    public ResponseEntity<List<Category>> GetAllCategories(){
        return ResponseEntity.ok(categoryService.GetAllCategories());
    }
}
