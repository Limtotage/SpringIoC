package com.example.springioc.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.config.JwtUtil;
import com.example.springioc.dto.AuthRequest;
import com.example.springioc.entity.Course;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Student;
import com.example.springioc.repository.UserRepo;
import com.example.springioc.service.CourseService;
import com.example.springioc.service.StudentService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo studentDB;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MyUser student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return ResponseEntity.ok(studentDB.save(student));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtUtil.GenerateToken(request.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("jwt", token));
    }


    @PostMapping("/course")
    public ResponseEntity<Course> CreateStudent(@RequestBody Course student) {
        return ResponseEntity.ok(courseService.CreateCourse(student));
    }


    @PostMapping("/student")
    public ResponseEntity<Student> CreateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.CreateStudent(student));
    }
}
