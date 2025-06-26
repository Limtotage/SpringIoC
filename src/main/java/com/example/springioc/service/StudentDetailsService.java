package com.example.springioc.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Student;
import com.example.springioc.repository.StudentRepo;

@Service
public class StudentDetailsService implements UserDetailsService{
    @Autowired
    private StudentRepo studentDB;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentDB.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Not Found.") );
        return new User(student.getUsername(),student.getPassword(),new ArrayList<>()); 
    }
}
