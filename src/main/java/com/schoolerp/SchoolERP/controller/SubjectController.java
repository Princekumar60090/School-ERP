package com.schoolerp.SchoolERP.controller;

import com.fasterxml.classmate.members.ResolvedParameterizedMember;
import com.schoolerp.SchoolERP.entity.Subject;
import com.schoolerp.SchoolERP.service.SubjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
@SecurityRequirement(name = "basicAuth")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@Valid @RequestBody Subject subject){
       try {
           subjectService.saveSubject(subject);
           return new ResponseEntity<>(subject, HttpStatus.CREATED);
       } catch (IllegalStateException e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
       }
    }

    @GetMapping
    public List<Subject> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable ObjectId id){
        Optional<Subject> subject = subjectService.getSubjectById(id);
        if(subject.isPresent()){
            return new ResponseEntity<>(subject.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable ObjectId id, @Valid @RequestBody Subject subjectDetails){
        Subject updatedSubject=subjectService.updateSubject(id,subjectDetails);
        if(updatedSubject!=null){
            return new ResponseEntity<>(updatedSubject,HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSubjectById(@PathVariable ObjectId id){
        subjectService.deleteSubjectById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
