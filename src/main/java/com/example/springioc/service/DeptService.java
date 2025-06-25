package  com.example.springioc.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Department;
import com.example.springioc.repository.DeptRepo;

@Service
public class DeptService{

    @Autowired
    private DeptRepo deptDB;


    public Department CreateDept(Department dept){
        if(dept.getStudents()!=null){
            dept.getStudents().forEach(x->x.setDepartment(dept));
        }
        return deptDB.save(dept);
    }

    public List<Department>  getAllDept() {
        return deptDB.findAll();
    }

    public void deleteById(Long id) {
        deptDB.deleteById(id);
    }
}
