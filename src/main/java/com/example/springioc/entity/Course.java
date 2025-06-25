package com.example.springioc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Courses")
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id") private Long id;

    @Column(name="course")private String coursename;

    public Long getId() {
        return id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}
