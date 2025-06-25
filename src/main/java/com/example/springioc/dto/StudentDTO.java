package com.example.springioc.dto;

public class StudentDTO {
    private Long id;
    private String faculty;
    private String department;
    private GeneralIDDTO generalID;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public GeneralIDDTO getGeneralID() {
        return generalID;
    }
    public void setGeneralID(GeneralIDDTO generalID) {
        this.generalID = generalID;
    }
    

    // getters setters
}

