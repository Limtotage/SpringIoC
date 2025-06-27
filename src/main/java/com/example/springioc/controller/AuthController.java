package com.example.springioc.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.config.JwtUtil;
import com.example.springioc.dto.AuthRequest;
import com.example.springioc.entity.MyUser;
import com.example.springioc.repository.UserRepo;
import com.example.springioc.service.MyUserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userDB;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MyUser user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER")); 
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userDB.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        MyUser user = userDetails.getUser();
        String token = jwtUtil.GenerateToken(user);
        return ResponseEntity.ok(Collections.singletonMap("jwt", token));
    }
}
