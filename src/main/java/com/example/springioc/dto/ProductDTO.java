package com.example.springioc.dto;

import com.example.springioc.enums.StockStatus;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer productStock; 
    private int SoldQuantity;
    private String categoryName;
    private StockStatus stockStatus;

}
