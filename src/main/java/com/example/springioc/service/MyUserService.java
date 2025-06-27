package com.example.springioc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.MyUser;
import com.example.springioc.repository.UserRepo;

@Service
public class MyUserService {
    @Autowired
    private UserRepo userDB;

    public List<MyUser> GetAllUsers() {
        return userDB.findAll();
    }
}
