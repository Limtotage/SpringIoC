package com.example.springioc.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private Long customerId;
    private List<OrderItemDTO> items;

    // Constructor, getters, setters
}