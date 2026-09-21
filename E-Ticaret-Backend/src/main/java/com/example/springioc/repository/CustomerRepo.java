package com.example.springioc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
        Optional<Customer> findByUser_Id(Long userId);
}