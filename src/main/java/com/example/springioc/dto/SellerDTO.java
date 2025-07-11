package com.example.springioc.dto;

import java.util.List;

import lombok.Data;

@Data
public class SellerDTO {
    private Long id;
    private List<Long> productsIds;
}
