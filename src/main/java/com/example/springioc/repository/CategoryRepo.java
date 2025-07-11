package com.example.springioc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springioc.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByIsApprovedTrue();
    
    List<Category> findByIsApprovedFalse();

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Optional<Category> findByName(@Param("name") String name);
}
