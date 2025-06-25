package com.example.springioc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Department;
import com.example.springioc.entity.Student;
import com.example.springioc.repository.DeptRepo;
import com.example.springioc.repository.StudentRepo;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentDB;

    @Autowired
    private DeptRepo deptDB;

    public Student CreateStudent(Student student) {
        // Department veritabanında var mı kontrol et
        Department dept = deptDB.findById(student.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department bulunamadı"));

        student.setDepartment(dept);

        return studentDB.save(student);
    }

    public List<Student> GetAllStudent() {
        return studentDB.findAll();
    }

    public void DeleteStudent(Long id) {
        studentDB.deleteById(id);
    }
}
