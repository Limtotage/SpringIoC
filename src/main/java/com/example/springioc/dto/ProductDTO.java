package com.example.springioc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int stock;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;
}
