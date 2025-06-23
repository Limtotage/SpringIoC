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

import com.example.crudappsingle.entity.Todo;
import com.example.crudappsingle.service.ToDoService;

@RestController
@RequestMapping("/users")
public class ToDoController {
    @Autowired
    private final ToDoService todoService;
    public ToDoController(ToDoService todoService){
        this.todoService=todoService;
    }
    @GetMapping
    public List<Todo> getAllToDos(){
        return todoService.GetAllToDos();
    }
    @PostMapping
    public Todo CreateToDo(@RequestBody Todo todo){
        return todoService.SaveToDO(todo);
    }
    @PutMapping("/{id}")
    public Todo UpdateToDo(@PathVariable Long id, @RequestBody Todo todo){
        return todoService.UpdateToDo(id, todo);
    }
    @DeleteMapping("/{id}")
    public void DeleteToDO(@PathVariable Long id){
        todoService.deleteToDo(id);
    }
}
