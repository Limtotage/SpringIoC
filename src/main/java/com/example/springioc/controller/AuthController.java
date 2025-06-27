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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication İşlemleri")
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
    @Operation(summary = "Kullanıcı kaydı yap.", description = "Yeni kullanıcı kaydı yapar ve kullanıcıyı döner.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Kayıt başarılı, kullanıcı döndürüldü.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MyUser.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Geçersiz kullanıcı bilgileri."),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Eklenmek istenen kullanıcı bilgileri", required = true, content = @Content(schema = @Schema(implementation = MyUser.class)))

    public ResponseEntity<?> register(@RequestBody MyUser user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userDB.save(user));
    }

    @PostMapping("/login")
    @Operation(summary = "Kullanıcı girişi yap.", description = "Kullanıcı adı ve şifre ile giriş yapar ve JWT token döner.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Giriş başarılı, JWT token döndürüldü.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Geçersiz kullanıcı adı veya şifre."),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Giriş için kullanıcı adı ve şifre", required = true, content = @Content(schema = @Schema(implementation = AuthRequest.class)))

    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        MyUser user = userDetails.getUser();
        String token = jwtUtil.GenerateToken(user);
        return ResponseEntity.ok(Collections.singletonMap("jwt", token));
    }
}
