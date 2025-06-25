package com.example.springioc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Course;
import com.example.springioc.entity.Student;
import com.example.springioc.repository.CourseRepo;
import com.example.springioc.repository.StudentRepo;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentDB;
    @Autowired
    private CourseRepo courseDB;

    public List<Student> GetAllStudents(){
        return studentDB.findAll();
    }
    public Student CreateStudent(Student student){
        List<Course> attachedCourses = new ArrayList<>();

        for (Course course : student.getCourses()) {
            Course attached = courseDB.findById(course.getId())
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + course.getId()));
            attachedCourses.add(attached);
        }

        student.setCourses(attachedCourses); // JPA'nın tanıdığı managed entity'leri kullan
        return studentDB.save(student);
    }

    public void DeleteStudent(Long id){
        studentDB.deleteById(id);
    }
}
