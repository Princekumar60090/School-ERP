package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.SchoolClass;
import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.service.SchoolClassService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {
    @Autowired
    private SchoolClassService schoolClassService;

    @PostMapping
    public ResponseEntity<?> createSchoolClass(@Valid @RequestBody SchoolClass schoolClass){
        try{
            SchoolClass newClass = schoolClassService.createSchoolClass(schoolClass);
            return new ResponseEntity<>(newClass, HttpStatus.CREATED);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping
    public List<SchoolClass> getAllSchoolClasses(){
        return schoolClassService.getAllSchoolClasses();
    }


    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getSchoolClassById(@PathVariable ObjectId id){
        Optional<SchoolClass> schoolClass = schoolClassService.getSchoolClassById(id);
        if(schoolClass.isPresent()){
            return new ResponseEntity<>(schoolClass.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{classId}/students/{studentId}")
    public ResponseEntity<?> addStudentToClass(@PathVariable ObjectId classId, @PathVariable ObjectId studentId) {
        try {
            SchoolClass updatedClass = schoolClassService.addStudentToClass(classId, studentId);
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/{classId}/subjects/{subjectId}")
    public ResponseEntity<?> addSubjectToClass(@PathVariable ObjectId classId, @PathVariable ObjectId subjectId) {
        try {
            SchoolClass updatedClass = schoolClassService.addSubjectToClass(classId, subjectId);
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }



    @PutMapping("/{classId}/assign-teacher/{teacherId}")
    public ResponseEntity<?> assignClassTeacher(@PathVariable ObjectId classId, @PathVariable ObjectId teacherId) {
        try {
            SchoolClass updatedClass = schoolClassService.assignClassTeacher(classId, teacherId);
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{classId}/teacher")
    public ResponseEntity<?> getClassTeacherDetails(@PathVariable ObjectId classId) {
        try {
            Teacher teacher = schoolClassService.getClassTeacherDetails(classId);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchoolClass(@PathVariable ObjectId id) {
        try {
            schoolClassService.deleteSchoolClass(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
