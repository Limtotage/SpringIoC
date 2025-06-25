// StudentController.java
package com.example.springioc.controller;

import java.util.List;

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

import com.example.springioc.entity.Department;
import com.example.springioc.service.DeptService;

@RestController
@RequestMapping("/department")
public class DeptController {

    @Autowired
    private DeptService deptService;
    @PostMapping
    public ResponseEntity<Department> CreateDepartment(@RequestBody Department dept) {
        return new ResponseEntity<>(deptService.CreateDept(dept), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Department> GetAllDepartments() {
        return deptService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteDepartment(@PathVariable Long id) {
        deptService.deleteById(id);
        return ResponseEntity.ok("Department with id " + id + " deleted successfully.");
    }
}


