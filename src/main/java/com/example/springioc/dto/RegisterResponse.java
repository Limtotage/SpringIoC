package com.example.springioc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String message;
    private String fullname;
    private String username;
    private String role;

    public RegisterResponse(String message, String fullname, String username, String role) {
        this.message = message;
        this.fullname = fullname;
        this.username = username;
        this.role = role;
    }
}
