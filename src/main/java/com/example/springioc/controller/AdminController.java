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
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Student;
import com.example.springioc.service.CourseService;
import com.example.springioc.service.MyUserService;
import com.example.springioc.service.StudentService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MyUserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/course")
    public ResponseEntity<Course> CreateStudent(@RequestBody Course student) {
        return ResponseEntity.ok(courseService.CreateCourse(student));
    }

    @PostMapping("/student")
    public ResponseEntity<Student> CreateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.CreateStudent(student));
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<String> DeleteCourse(@PathVariable Long id) {
        courseService.DeleteCourse(id);
        return ResponseEntity.ok("Ders Silindi.");
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> DeleteStudent(@PathVariable Long id) {
        studentService.DeleteStudent(id);
        return ResponseEntity.ok("Öğrenci silindi");
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> GetAllStudents() {
        return ResponseEntity.ok(studentService.GetAllStudents());
    }

    @GetMapping("/course")
    public ResponseEntity<List<Course>> GetAllCourses() {
        return ResponseEntity.ok(courseService.GetAllCourses());
    }
    @GetMapping("/user")
    public ResponseEntity<List<MyUser>> GetAllUsers(){
        return ResponseEntity.ok(userService.GetAllUsers());
    }
}
