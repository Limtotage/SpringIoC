package com.example.springioc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.entity.Student;
import com.example.springioc.service.StudentService;
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.CreateStudent(student);
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.GetAllStudent();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.DeleteStudent(id);
    }
}
