package com.example.springioc.util;

import com.example.springioc.dto.GeneralIDDTO;
import com.example.springioc.dto.StudentDTO;
import com.example.springioc.entity.GeneralID;
import com.example.springioc.entity.Student;

public class MapperUtil {

    public static StudentDTO toStudentDTO(Student student) {
        if (student == null) return null;
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFaculty(student.getFaculty());
        dto.setDepartment(student.getDepartment());
        dto.setGeneralID(toGeneralIDDTO(student.getGeneralID()));
        return dto;
    }

    public static GeneralIDDTO toGeneralIDDTO(GeneralID generalID) {
        if (generalID == null) return null;
        GeneralIDDTO dto = new GeneralIDDTO();
        dto.setId(generalID.getId());
        dto.setName(generalID.getName());
        dto.setSurname(generalID.getSurname());
        return dto;
    }

    public static Student toStudent(StudentDTO dto) {
        if (dto == null) return null;
        Student student = new Student();
        student.setId(dto.getId());
        student.setFaculty(dto.getFaculty());
        student.setDepartment(dto.getDepartment());
        student.setGeneralID(toGeneralID(dto.getGeneralID()));
        if (student.getGeneralID() != null) {
            student.getGeneralID().setStudent(student);  // Bidirectional ilişkiyi set et
        }
        return student;
    }

    public static GeneralID toGeneralID(GeneralIDDTO dto) {
        if (dto == null) return null;
        GeneralID generalID = new GeneralID();
        generalID.setId(dto.getId());
        generalID.setName(dto.getName());
        generalID.setSurname(dto.getSurname());
        return generalID;
    }
    public static void DeleteGeneralID(Long id){
        
    }
}
