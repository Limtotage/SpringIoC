package com.example.springioc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Seller;

public interface SellerRepo extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUser_Id(Long userId);

}
