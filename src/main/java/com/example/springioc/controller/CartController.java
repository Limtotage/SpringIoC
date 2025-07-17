package com.example.springioc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.CartDTO;
import com.example.springioc.dto.CartItemDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.service.CartService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CustomerRepo customerDB;
    private final AuthComponents authComponents;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        Cart cart = getCartByCustomer(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartService.getCart(cart));
    }



    @PostMapping("/add/{user_id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addItem(@PathVariable Long user_id, @RequestBody CartItemDTO dto) {
        Cart cart = getCartByCustomer(user_id);
        cartService.addItemToCart(cart, dto.getProductId(), dto.getQuantity());
        return ResponseEntity.ok().build();
    }

    // Sepeti boşalt
    @DeleteMapping("/{customerId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        Cart cart = getCartByCustomer(customerId);
        cartService.clearCart(cart);
        return ResponseEntity.noContent().build();
    }

    // Yardımcı Method
    private Cart getCartByCustomer(Long id) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        System.out.println(userId + " " + id);
        if (!userId.equals(id) && !isAdmin) {
            throw new EntityNotFoundException("You can only Edit your own Cart account");
        }
        return customerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user ID: " + userId))
                .getCart();

    }
}
