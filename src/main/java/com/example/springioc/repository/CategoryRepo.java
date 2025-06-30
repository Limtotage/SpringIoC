package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
