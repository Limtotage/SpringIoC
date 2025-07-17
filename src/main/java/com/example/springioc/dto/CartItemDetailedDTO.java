package com.example.springioc.dto;

import com.example.springioc.enums.StockStatus;

import lombok.Data;

@Data
public class CartItemDetailedDTO {
    private Long id;
    private int quantity;
    private int stockQuantity;
    private Long productId;
    private String productName;
    private String categoryName;
    private double productPrice;
    private StockStatus stockStatus;

}
