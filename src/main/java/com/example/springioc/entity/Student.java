package com.example.springioc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id") private Long id;

    @Column(name="faculty") private String faculty;
    @Column(name="departman") private String department;

    @OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
    @JoinColumn(name = "general_id")
    @JsonIgnoreProperties(value={"student"})
    private GeneralID generalID;

    public Long getId() {
        return id;
    }


    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public GeneralID getGeneralID() {
        return generalID;
    }

    public void setGeneralID(GeneralID generalID) {
        this.generalID = generalID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
