package com.example.springioc.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="StudentID")
public class StudentID {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade=CascadeType.ALL , orphanRemoval=true)
    @JoinColumn(name="generalID")
    private GeneralID generalID;




    public Long getId() {
        return id;
    }


    public GeneralID getGeneralID() {
        return generalID;
    }


    public void setGeneralID(GeneralID generalID) {
        this.generalID = generalID;
    }
    /*
    private String Faculty;
    private String Department;
    private String Program;
    private Long StudentIDNo;

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String Faculty) {
        this.Faculty = Faculty;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getProgram() {
        return Program;
    }

    public void setProgram(String Program) {
        this.Program = Program;
    }

    public Long getStudentIDNo() {
        return StudentIDNo;
    }

    public void setStudentIDNo(Long StudentIDNo) {
        this.StudentIDNo = StudentIDNo;
    }*/


}
