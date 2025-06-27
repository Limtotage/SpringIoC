package com.example.springioc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.entity.Course;
import com.example.springioc.entity.Student;
import com.example.springioc.service.CourseService;
import com.example.springioc.service.StudentService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/student")
    public ResponseEntity<List<Student>> GetAllStudents() {
        return ResponseEntity.ok(studentService.GetAllStudents());
    }

    @GetMapping("/course")
    public ResponseEntity<List<Course>> GetAllCourses() {
        return ResponseEntity.ok(courseService.GetAllCourses());
    }
}
