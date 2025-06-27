package com.example.springioc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.entity.Course;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Student;
import com.example.springioc.service.CourseService;
import com.example.springioc.service.MyUserService;
import com.example.springioc.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Yönetici İşlemleri")
public class AdminController {
    @Autowired
    private MyUserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/course")
    @Operation(summary = "Yeni ders oluşturur.", description = "Yeni bir ders oluşturmak için bu endpoint'i kullanabilirsiniz. Ders bilgilerini içeren bir JSON nesnesi gönderin.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ders başarıyla oluşturuldu.",
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Course.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Geçersiz ders bilgileri."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin yetkisi gerekmektedir."),
    })
    public ResponseEntity<Course> CreateCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.CreateCourse(course));
    }

    @PostMapping("/student")
    @Operation(summary = "Yeni öğrenci oluşturur.",
            description = "Yeni bir öğrenci oluşturmak için bu endpoint'i kullanabilirsiniz. Öğrenci bilgilerini içeren bir JSON nesnesi gönderin.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Öğrenci başarıyla oluşturuldu.",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Geçersiz öğrenci bilgileri."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin yetkisi gerekmektedir."),
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Eklenmek isten öğrenci bilgileri", required = true, content = @Content(schema = @Schema(implementation = Student.class)))
    public ResponseEntity<Student> CreateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.CreateStudent(student));
    }

    @Operation(
        summary = "Ders silme işlemi.",
        description="Belirtilen ID'ye sahip dersi siler.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ders başarıyla silindi."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Ders bulunamadı."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin yetkisi gerekmektedir.")
        }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Silinecek dersin ID'si",
        required = true,
        content = @Content(schema = @Schema(type = "long", format = "int64"))
    )
    @DeleteMapping("/course/{id}")
    public ResponseEntity<String> DeleteCourse(@PathVariable Long id) {
        courseService.DeleteCourse(id);
        return ResponseEntity.ok("Ders Silindi.");
    }
    
    @Operation(summary = "Öğrenci silme işlemi.",
            description = "Belirtilen ID'ye sahip öğrenciyi siler.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Öğrenci başarıyla silindi."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Öğrenci bulunamadı."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin yetkisi gerekmektedir.")
            }) @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Silinecek öğrencinin ID'si",
            required = true,
            content = @Content(schema = @Schema(type = "long", format = "int64"))
    )
    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> DeleteStudent(@PathVariable Long id) {
        studentService.DeleteStudent(id);
        return ResponseEntity.ok("Öğrenci silindi");
    }

    @Operation(summary = "Tüm öğrencileri listeler.",description="Veri tabanındaki tüm öğrencileri listelemek için bu endpoint'i kullanabilirsiniz.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Öğrenciler başarıyla listelendi.",
                        content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin/User yetkisi gerekmektedir.")
            })
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Tüm öğrencileri listelemek için bu endpoint'i kullanabilirsiniz.",
            required = true,
            content = @Content(schema = @Schema(implementation = Student.class))
    )
    @GetMapping("/student")
    public ResponseEntity<List<Student>> GetAllStudents() {
        return ResponseEntity.ok(studentService.GetAllStudents());
    }

    @Operation(summary = "Tüm dersleri listele.",description="Veri tabanındaki tüm dersleri listelemek için bu endpoint'i kullanabilirsiniz.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dersler başarıyla listelendi.",
                        content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Course.class))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin/User yetkisi gerekmektedir.")
            })
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Tüm dersleri listelemek için bu endpoint'i kullanabilirsiniz.",
            required = true,
            content = @Content(schema = @Schema(implementation = Course.class))
    )
    @GetMapping("/course")
    public ResponseEntity<List<Course>> GetAllCourses() {
        return ResponseEntity.ok(courseService.GetAllCourses());
    }
    @GetMapping("/user")
    @Operation(summary = "Tüm kullanıcıları listele.",
            description = "Veri tabanındaki tüm kullanıcıları listelemek için bu endpoint'i kullanabilirsiniz.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Kullanıcılar başarıyla listelendi.",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = MyUser.class))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Bu İşlem için Admin yetkisi gerekmektedir.")
            })
    public ResponseEntity<List<MyUser>> GetAllUsers() {
        return ResponseEntity.ok(userService.GetAllUsers());
    }
}
