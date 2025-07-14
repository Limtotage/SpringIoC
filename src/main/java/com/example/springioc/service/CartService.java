package com.example.springioc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.CartDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Product;
import com.example.springioc.mapper.CartMapper;
import com.example.springioc.repository.CartItemRepo;
import com.example.springioc.repository.CartRepo;
import com.example.springioc.repository.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartItemRepo cartItemDB;

    @Autowired
    private CartRepo cartDB;

    @Autowired
    private ProductRepo productDB;
    @Autowired
    private CartMapper cartMapper;

    public CartDTO getCart(Cart cart) {
        cart.calculateTotalPrice();
        return cartMapper.toDto(cart);
    }

    public void addItemToCart(Cart cart, Long productId, int quantity) {
        Product product = productDB.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
        
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        cart.calculateTotalPrice();

        cartDB.save(cart);
    }
    public void RemoveItemFromCart(Cart cart, Long productId) {
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.calculateTotalPrice();
        cartDB.save(cart);
    }
    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartDB.save(cart);
    }
}
