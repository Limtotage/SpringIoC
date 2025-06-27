package com.example.springioc.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.springioc.entity.MyUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Component
@Tag(name = "JWT İşlemleri")
public class JwtUtil {
    private final String secret = "benbuuzunluktabirsifreolusturdum";
    private final long expirationMs = 1000 * 60 * 60;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    @Operation(summary = "JWT Token Oluşturma")
    public String GenerateToken(MyUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    @SuppressWarnings("unchecked")
    @Operation(summary = "JWT Token'dan Roller Çıkarma")
    public List<String> ExtractRoles(String token) {
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }
    @Operation(summary = "JWT Token'dan Kullanıcı Adı Çıkarma")
    public String ExtractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Operation(summary = "JWT Token Doğrulama")
    public boolean ValidateToken(String token, UserDetails userDetails) {
        String username = ExtractUsername(token);
        return username.equals(userDetails.getUsername()) && !IsTokenExpired(token);
    }

    @Operation(summary = "JWT Token'ın Süresinin Dolup Dolmadığını Kontrol Etme")
    private boolean IsTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
