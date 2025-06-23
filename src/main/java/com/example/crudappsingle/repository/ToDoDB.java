package com.example.crudappsingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crudappsingle.entity.Todo;

public interface ToDoDB extends JpaRepository<Todo, Long>{

}