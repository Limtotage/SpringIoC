package com.example.springioc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GeneralID")
public class GeneralID {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy="generalID")
    @JsonBackReference
    private StudentID studentID;

    private String surname;
    private String name;

    public Long getId() {
        return id;
    }


    public StudentID getStudentID() {
        return studentID;
    }

    public void setStudentID(StudentID studentID) {
        this.studentID = studentID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
