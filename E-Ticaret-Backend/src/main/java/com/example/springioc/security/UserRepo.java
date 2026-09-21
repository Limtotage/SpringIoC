package com.example.springioc.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.MyUser;

public interface UserRepo extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
    boolean existsByUsername(String username);
}