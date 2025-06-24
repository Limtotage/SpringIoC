package com.example.crudappsingle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crudappsingle.entity.User;
import com.example.crudappsingle.repository.UserDB;

@Service
public class UserService {
    private final UserDB userDB;

    public UserService(UserDB userDB){
        this.userDB=userDB;
    }
    public List<User> GetAllUsers(){
        return userDB.findAll();
    }
    public User SaveUser(User User){
        return userDB.save(User);
    }
    public User UpdateUser(Long id,User user){
        User newUser= userDB.findById(id).orElseThrow();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        return userDB.save(newUser);
    }
    public void DeleteUser(Long id){
        userDB.deleteById(id);
    }
}
