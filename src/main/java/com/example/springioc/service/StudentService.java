package com.example.springioc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springioc.dto.StudentCreateRequest;
import com.example.springioc.entity.GeneralID;
import com.example.springioc.entity.StudentID;
import com.example.springioc.repository.GeneralRepo;
import com.example.springioc.repository.StudentRepo;

@Service
public class StudentService {
    private final StudentRepo studentDB;
    private final GeneralRepo generalDB;

    public StudentService(GeneralRepo generalDB, StudentRepo studentDB) {
        this.generalDB = generalDB;
        this.studentDB = studentDB;
    }

    public StudentID CreateStudent(StudentCreateRequest newstudent){
        GeneralID general = new GeneralID();
        general.setName(newstudent.name);
        general.setSurname(newstudent.surname);
        generalDB.save(general);

        StudentID student = new StudentID();
        student.setGeneralID(general);
        return studentDB.save(student);
    }

    public List<StudentID> GetAllStudents(){
        return studentDB.findAll();
    }
    public void deleteById(Long id) {
        studentDB.deleteById(id);
    }

}
