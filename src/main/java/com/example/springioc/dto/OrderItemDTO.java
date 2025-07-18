package com.example.springioc.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String productName;
    private int quantity;
    private Double subtotal;
}
