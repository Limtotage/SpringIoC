package com.example.springioc.dto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Request Data Transfer Object")
public class AuthRequest {
    private String username;
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

