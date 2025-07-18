package com.example.springioc.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class OrderItemEmbedded {
    private String productName;
    private int quantity;
    private Double subtotal;
}