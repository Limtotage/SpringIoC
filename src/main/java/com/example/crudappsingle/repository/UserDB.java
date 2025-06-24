package com.example.crudappsingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crudappsingle.entity.User;

public interface UserDB extends JpaRepository<User, Long>{
    
}