package com.example.springioc.dto;

import lombok.Data;

@Data
public class CartItemDetailedDTO {
    private Long id;
    private int quantity;
    private Long productId;
    private String productName;
    private double productPrice;
}
