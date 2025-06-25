package  com.example.springioc.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Student;
import com.example.springioc.repository.StudentRepo;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentDB;

    public Student saveStudent(Student student) {
        if (student.getGeneralID() != null) {
            student.getGeneralID().setStudent(student);
        }
        return studentDB.save(student);
    }

    public List<Student> getAll() {
        return studentDB.findAll();
    }

    public void deleteById(Long id) {
        studentDB.deleteById(id);
    }
}
