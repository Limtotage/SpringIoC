package com.example.springioc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springioc.dto.StudentCreateRequest;
import com.example.springioc.entity.GeneralID;
import com.example.springioc.entity.StudentID;
import com.example.springioc.repository.GeneralRepo;

@Service
public class GeneralService {
    private final GeneralRepo generalDB;

    public GeneralService(GeneralRepo generalDB) {
        this.generalDB = generalDB;
    }

    public GeneralID CreateStudent(StudentCreateRequest newstudent){
        GeneralID general = new GeneralID();
        general.setName(newstudent.name);
        general.setSurname(newstudent.surname);

        StudentID student = new StudentID();
        general.setStudentID(student);
        student.setGeneralID(general);
        return generalDB.save(general);
    }

    public List<GeneralID> GetAllStudents(){
        return generalDB.findAll();
    }
    public void deleteById(Long id) {
        generalDB.deleteById(id);
    }

}
