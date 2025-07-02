package com.example.springioc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.service.CategoryService;


@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<CategoryDTO> CreateCategory(@RequestBody CategoryDTO categoryDTO,
            Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(categoryService.CreateCategory(categoryDTO, isAdmin));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Optional<CategoryDTO>> GetCategoryByID(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.GetCategoryByID(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<CategoryDTO>> GetAllCategories() {
        return ResponseEntity.ok(categoryService.GetAllCategories());
    }

    @GetMapping("/approved")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryDTO>> GetAllApprovedCategories() {
        return ResponseEntity.ok(categoryService.GetAllApprovedCategories());
    }

    @GetMapping("/unapproved")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryDTO>> GetAllUnapprovedCategories() {
        return ResponseEntity.ok(categoryService.GetAllUnapprovedCategories());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<CategoryDTO> UpdateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.UpdateCategory(id, categoryDTO));
    }

    @PutMapping("/categories/{id}/approved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveCategory(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/approved")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> UpdateCategoryApproval(@PathVariable Long id, @RequestBody Boolean isApproved) {
        return ResponseEntity.ok(categoryService.UpdateCategoryApproval(id, isApproved));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<String> DeleteCategory(@PathVariable Long id) {
        categoryService.DeleteCategory(id);
        return ResponseEntity.ok("Kategori silindi");
    }
}
