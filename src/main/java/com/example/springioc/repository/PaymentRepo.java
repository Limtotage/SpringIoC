package com.example.springioc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
