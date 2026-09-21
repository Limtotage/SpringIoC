package com.example.springioc.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Boolean isApproved;
    private List<Long> productsIds;
}