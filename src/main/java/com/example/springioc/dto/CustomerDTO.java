package com.example.springioc.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String fullname;
    private String username;
    private List<ProductDTO> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
