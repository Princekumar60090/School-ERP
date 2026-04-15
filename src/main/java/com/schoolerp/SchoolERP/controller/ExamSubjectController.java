package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.ExamSubject;
import com.schoolerp.SchoolERP.service.ExamSubjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-subjects")
@SecurityRequirement(name = "basicAuth")
public class ExamSubjectController {

    @Autowired
    private ExamSubjectService examSubjectService;

    // ADD SUBJECT TO EXAM
    @PostMapping
    public ResponseEntity<ExamSubject> addSubjectToExam(@RequestBody ExamSubject examSubject) {
        ExamSubject saved = examSubjectService.addSubjectToExam(examSubject);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET SUBJECTS FOR EXAM
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ExamSubject>> getExamSubjectsForExam(@PathVariable ObjectId examId) {
        List<ExamSubject> examSubjects = examSubjectService.getExamSubjectsForExam(examId);
        return new ResponseEntity<>(examSubjects, HttpStatus.OK);
    }

    // UPDATE SUBJECT SETTINGS
    @PutMapping("/{examSubjectId}")
    public ResponseEntity<ExamSubject> updateExamSubject(
            @PathVariable ObjectId examSubjectId,
            @RequestBody ExamSubject examSubject) {

        ExamSubject updatedSubject = examSubjectService.updateSubjectSettings(examSubjectId, examSubject);
        return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
    }

    // DELETE SUBJECT FROM EXAM
    @DeleteMapping("/{examSubjectId}")
    public ResponseEntity<String> deleteExamSubject(@PathVariable ObjectId examSubjectId) {
        examSubjectService.deleteSubjectFromExam(examSubjectId);
        return ResponseEntity.ok("Subject removed successfully");
    }

    // GET SINGLE SUBJECT (exam + subject)
    @GetMapping("/{examId}/{subjectId}")
    public ResponseEntity<ExamSubject> getExamSubject(
            @PathVariable ObjectId examId,
            @PathVariable ObjectId subjectId) {

        ExamSubject examSubject = examSubjectService.getExamSubject(examId, subjectId);
        return ResponseEntity.ok(examSubject);
    }
}
