package com.example.springioc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.entity.Course;
import com.example.springioc.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService service;

    @PostMapping
    public ResponseEntity<Course> CreateStudent(@RequestBody Course student){
        return ResponseEntity.ok(service.CreateCourse(student));
    }

    @GetMapping
    public ResponseEntity<List<Course>> GetAllStudents(){
        return ResponseEntity.ok(service.GetAllCourses());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteStudent(@PathVariable Long id){
        service.DeleteCourse(id);
        return ResponseEntity.ok("Ders Silindi.");
    }
}
