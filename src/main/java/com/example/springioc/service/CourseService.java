package com.example.springioc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Course;
import com.example.springioc.entity.Student;
import com.example.springioc.repository.CourseRepo;

@Service
public class CourseService {
    @Autowired
    private CourseRepo courseDB;

    public List<Course> GetAllCourses() {
        return courseDB.findAll();
    }

    public Course CreateCourse(Course course) {
        return courseDB.save(course);
    }

    public void DeleteCourse(Long id) {
        Course course = courseDB.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        for (Student student : course.getStudents()) {
            student.getCourses().remove(course); 
        }
        course.getStudents().clear(); 
        courseDB.delete(course);
    }
}
