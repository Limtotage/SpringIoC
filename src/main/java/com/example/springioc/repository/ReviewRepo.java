package com.example.springioc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
}