package com.example.springioc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.CartItemDetailedDTO;
import com.example.springioc.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping("/{user_id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CartItemDetailedDTO>> getCartItems(@PathVariable Long user_id) {
        return ResponseEntity.ok(cartItemService.getAllCartItems(user_id));
    }

    @PutMapping("/decrease/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> decreaseItemQuantity(@PathVariable Long id) {
        System.out.println("\n\n Decrease item quantity");
        cartItemService.DecreaseItemQuantity(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/increase/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> increaseItemQuantity(@PathVariable Long id) {
        try {
            cartItemService.IncreaseItemQuantity(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Stokta yeterli ürün yok.");
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> removeItem(@PathVariable Long id) {
        cartItemService.DeleteCartItem(id);
        return ResponseEntity.ok("Item removed successfully");
    }

}
