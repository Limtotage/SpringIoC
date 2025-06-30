package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}