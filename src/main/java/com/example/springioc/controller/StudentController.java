// StudentController.java
package com.example.springioc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.StudentDTO;
import com.example.springioc.entity.Student;
import com.example.springioc.service.StudentService;
import com.example.springioc.util.MapperUtil;

@RestController
@RequestMapping("/students")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = MapperUtil.toStudent(studentDTO);
        Student saved = studentService.saveStudent(student);
        StudentDTO responseDTO = MapperUtil.toStudentDTO(saved);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentService.getAll();
        return students.stream()
                .map(MapperUtil::toStudentDTO)
                .collect(Collectors.toList());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.ok("Student with id " + id + " deleted successfully.");
    }
}


