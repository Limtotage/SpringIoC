package com.example.springioc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.CartItemDTO;
import com.example.springioc.mapper.CartItemMapper;
import com.example.springioc.repository.CartItemRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {
    @Autowired
    private CartItemRepo cartItemDB;
    @Autowired
    private CartItemMapper cartItemMapper;

    public CartItemDTO  getCartItem(Long id) {
        return cartItemDB.findById(id)
                .map(cartItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found with ID: " + id));
    }
    public void DeleteCartItem(Long id) {
        if (!cartItemDB.existsById(id)) {
            throw new EntityNotFoundException("Cart item not found with ID: " + id);
        }
        cartItemDB.deleteById(id);
    }
}
