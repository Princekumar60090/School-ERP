package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.Student;
import com.schoolerp.SchoolERP.service.StudentService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/grade")
    public List<Student> getAllStudentsOfParticularGrade(int grade) {
        return studentService.getStudentsByGrade(grade);
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student){
        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable ObjectId id){
        Optional<Student> student = studentService.getStudentById(id);
        if(student.isPresent()){
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable ObjectId id, @Valid @RequestBody Student studentDetails){
        Student updatedStudent  = studentService.updateStudent(id,studentDetails);
        if(updatedStudent!=null){
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable ObjectId id){
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/rollno/{rollNo}")
    public ResponseEntity<Student> getStudentByRollNo(@PathVariable String rollNo) {
        Optional<Student> student = studentService.getStudentByRollNo(rollNo);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
