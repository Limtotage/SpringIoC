package com.example.springioc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.service.CategoryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Optional<CategoryDTO>> GetCategoryByID(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.GetCategoryByID(id));
    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<CategoryDTO>> GetAllCategories(){
        return ResponseEntity.ok(categoryService.GetAllCategories());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<CategoryDTO> UpdateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.UpdateCategory(id, categoryDTO));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<String> DeleteCategory(@PathVariable Long id) {
        categoryService.DeleteCategory(id);
        return ResponseEntity.ok("Kategori silindi");
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<CategoryDTO> CreateCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.CreateCategory(categoryDTO));
    }
}
