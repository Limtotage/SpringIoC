package com.example.springioc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.MyUser;
import com.example.springioc.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepo userDB;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userDB.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Not Found.") );
        return new MyUserDetails(myUser);
    }



}
