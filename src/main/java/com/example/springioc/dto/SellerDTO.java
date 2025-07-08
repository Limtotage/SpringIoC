package com.example.springioc.dto;

import java.util.List;

import lombok.Data;

@Data
public class SellerDTO {
    private Long id;
    private String username;
    private List<Long> productsIds;
}
