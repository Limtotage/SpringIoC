package com.example.springioc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.springioc.service.MyUserDetails;
import com.example.springioc.service.MyUserDetailsService;

@Component
public class AuthComponents {

    @Autowired
    private MyUserDetailsService userDetailsService;

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
    

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof MyUserDetails userDetails) {
            return userDetails.getId();
        } else if (principal instanceof String username) {
            MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
            return userDetails.getId();
        }

        throw new RuntimeException("Kullanıcı kimliği alınamadı.");
    }
}
