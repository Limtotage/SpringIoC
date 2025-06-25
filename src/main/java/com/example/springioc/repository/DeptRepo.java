package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Department;

public interface DeptRepo extends JpaRepository<Department,Long >{
    
}