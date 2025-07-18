package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
