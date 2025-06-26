package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long>{
}
