package com.example.crudappsingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crudappsingle.entity.Reminder;

public interface ReminderDB extends JpaRepository<Reminder, Long>{
    
}
