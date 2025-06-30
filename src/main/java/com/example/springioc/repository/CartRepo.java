package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long customerId);
}