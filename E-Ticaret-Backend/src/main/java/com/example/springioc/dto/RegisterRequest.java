package com.example.springioc.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Full name is required")
    private String fullname;

    @NotBlank(message = "Password is required")
    private String password;
    private Set<String> roles; 
}
