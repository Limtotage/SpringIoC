package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.Course;

public interface CourseRepo extends JpaRepository<Course,Long >{
    
}