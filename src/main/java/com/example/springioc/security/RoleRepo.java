package com.example.springioc.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
}