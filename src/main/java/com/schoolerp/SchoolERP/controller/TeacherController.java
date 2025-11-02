package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.Student;
import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.service.StudentService;
import com.schoolerp.SchoolERP.service.TeacherService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @PostMapping
    public ResponseEntity<Teacher> saveTeacher(@Valid @RequestBody Teacher teacher){
        teacherService.saveTeacher(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable ObjectId id){
        Optional<Teacher> teacher= teacherService.getTeacherById(id);
        if(teacher.isPresent()){
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@Valid @RequestBody Teacher teacher, @PathVariable ObjectId id){
        Teacher updatedTeacher  = teacherService.updateTeacher(id,teacher);
        if(updatedTeacher!=null){
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable ObjectId id){
        teacherService.deleteTeacherById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
