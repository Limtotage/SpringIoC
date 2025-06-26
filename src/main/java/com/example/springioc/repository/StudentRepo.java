package com.example.springioc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long>{
    Optional<Student> findByUsername(String username);

    boolean existsByUsername(String username);
}
