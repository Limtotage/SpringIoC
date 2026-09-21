package com.example.springioc.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.ProductDTO;
import com.example.springioc.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/visitor")
@RequiredArgsConstructor
public class VisitorController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.GetAllProducts());
    }
}
