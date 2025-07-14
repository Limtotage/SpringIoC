package com.example.springioc.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartDTO {
    private Long id;
    private Double totalPrice;
    private List<Long> cartItemIds = new ArrayList<>();
}
