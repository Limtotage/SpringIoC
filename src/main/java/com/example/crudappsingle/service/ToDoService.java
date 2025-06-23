package com.example.crudappsingle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crudappsingle.entity.Todo;
import com.example.crudappsingle.repository.ToDoDB;

@Service
public class ToDoService {
    private final ToDoDB todoDB;

    public ToDoService(ToDoDB todoDB) {
        this.todoDB = todoDB;
    }

    public List<Todo> GetAllToDos() {
        return todoDB.findAll();
    }

    public Todo SaveToDO(Todo todo) {
        return todoDB.save(todo);
    }

    public Todo UpdateToDo(Long id, Todo todo) {
        Todo newtodo = todoDB.findById(id).orElseThrow();
        newtodo.setTodo(todo.getTodo());
        newtodo.setIsCompleted(todo.getIsCompleted());
        return todoDB.save(newtodo);
    }
    public void deleteToDo(Long id){
        todoDB.deleteById(id);
    }
}
