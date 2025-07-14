package com.example.springioc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerRepo customerDB;

    private Cart getCartByCustomer(Long customerId) {
        return customerDB.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for customer ID: " + customerId))
                .getCart();

    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        Cart cart = getCartByCustomer(id);
        return ResponseEntity.ok(cartService.getCart(cart));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Void> addItem(@PathVariable Long id,@RequestBody CartItemDTO dto) {
        Cart cart = getCartByCustomer(id);
        cartService.addItemToCart(cart, dto.getProductId(), dto.getQuantity());
        return ResponseEntity.ok().build();
    }

    // Ürün çıkar
    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        Cart cart = getCartByCustomer(customerId);
        cartService.RemoveItemFromCart(cart, productId);
        return ResponseEntity.noContent().build();
    }

    // Sepeti boşalt
    @DeleteMapping("/{customerId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        Cart cart = getCartByCustomer(customerId);
        cartService.clearCart(cart);
        return ResponseEntity.noContent().build();
    }
}
