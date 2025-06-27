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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User İşlemleri")
public class UserController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/student")
    @Operation(summary = "Tüm öğrencileri listele.", description = "Sistemde kayıtlı olan tüm öğrencilerin listesini döner."
    ,responses= {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Öğrenciler başarıyla listelendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Öğrenci bulunamadı.")
    })

    public ResponseEntity<List<Student>> GetAllStudents() {
        return ResponseEntity.ok(studentService.GetAllStudents());
    }

    @GetMapping("/course")
    @Operation(summary = "Tüm dersleri listele.", description = "Sistemde kayıtlı olan tüm derslerin listesini döner.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dersler başarıyla listelendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Ders bulunamadı.")
    })
    public ResponseEntity<List<Course>> GetAllCourses() {
        return ResponseEntity.ok(courseService.GetAllCourses());
    }
}

