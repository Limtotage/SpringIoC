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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "icreatethisandaverylongsecretkey";
    private final long expirationMs = 1000 * 60 * 60;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String GenerateToken(MyUser myUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", myUser.getRoles());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(myUser.getUsername())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return (List<String>) extractClaims(token).get("roles", List.class);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    public Boolean IsTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public Boolean ValidateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !IsTokenExpired(token));
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    

}
