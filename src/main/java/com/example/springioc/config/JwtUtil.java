package com.example.springioc.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.springioc.entity.MyUser;
import com.example.springioc.service.MyUserDetails;

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

    public String GenerateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails) userDetails;
            // Buradan orijinal MyUser nesnesine ulaşabilirsin
            MyUser user = myUserDetails.getUser();
            claims.put("roles", user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toList()));
        } else {
            // Eğer başka bir UserDetails türüyse, fallback olarak authorities kullan
            claims.put("roles", userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList()));
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
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
