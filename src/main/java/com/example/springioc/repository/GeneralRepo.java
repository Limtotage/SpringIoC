package com.example.springioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.GeneralID;

public interface GeneralRepo extends JpaRepository<GeneralID,Long >{
    
}
