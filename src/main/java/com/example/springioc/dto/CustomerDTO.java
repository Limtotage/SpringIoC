package com.example.springioc.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String fullname;
    private String username;
    private String password;
    private List<Long> productsIds;
}
