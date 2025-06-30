package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
