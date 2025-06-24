package com.example.crudappsingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudappsingle.entity.User;
import com.example.crudappsingle.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> GetAllUsers(){
        return userService.GetAllUsers();
    }
    @PostMapping
    public User SaveUser(@RequestBody User user){
        return userService.SaveUser(user);
    }
    @PutMapping("/{id}")
    public User UpdateUser(@PathVariable Long id, @RequestBody User user){
        return userService.UpdateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public void DeleteReminder(@PathVariable Long id){
        userService.DeleteUser(id);
    }
}
