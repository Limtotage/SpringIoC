package com.example.springioc.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private boolean enabled;
    private Set<String> roleNames;
}
