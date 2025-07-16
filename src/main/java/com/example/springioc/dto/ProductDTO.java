package com.example.springioc.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer productStock; 
    private String categoryName;
}
