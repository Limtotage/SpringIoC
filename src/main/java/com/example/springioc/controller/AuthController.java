package com.example.springioc.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.config.JwtUtil;
import com.example.springioc.dto.AuthRequest;
import com.example.springioc.dto.AuthResponse;
import com.example.springioc.dto.RegisterRequest;
import com.example.springioc.dto.RegisterResponse;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Role;
import com.example.springioc.entity.Seller;
import com.example.springioc.mapper.CustomerMapper;
import com.example.springioc.mapper.SellerMapper;
import com.example.springioc.security.RoleRepo;
import com.example.springioc.security.UserRepo;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.SellerRepo;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepo userDB;
    @Autowired
    private RoleRepo roleDB;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepo customerDB;
    @Autowired
    private SellerRepo sellerDB;

    @GetMapping
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("Welcome to the Spring IoC Application!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.GenerateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        MyUser user = new MyUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());

        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            Role defaultRole = roleDB.findByName("ROLE_CUSTOMER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRoles(Set.of(defaultRole));
        } else {
            Set<Role> attachedRoles = registerRequest.getRoles().stream()
                    .map(roleName -> roleDB.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(attachedRoles);
        }
        if (userDB.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("This Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDB.save(user);
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_CUSTOMER"))) {
            Customer customer = new Customer();
            customer.setUser(user);
            customerDB.save(customer);
        } else if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_SELLER"))) {
            Seller seller = new Seller();
            seller.setUser(user);
            sellerDB.save(seller);
        }
        return ResponseEntity.ok(new RegisterResponse(
                "User registered successfully",
                user.getUsername(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "))));
    }
}
