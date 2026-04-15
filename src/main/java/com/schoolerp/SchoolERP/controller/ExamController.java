package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.Exam;
import com.schoolerp.SchoolERP.service.ExamService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@SecurityRequirement(name = "basicAuth")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> addExam(@RequestBody Exam exam) {
        Exam savedExam = examService.createExam(exam);
        return new ResponseEntity<>(savedExam, HttpStatus.OK);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<Exam> getExamById(@PathVariable ObjectId examId) {
        Exam exam = examService.getExamById(examId);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }


    // ---------------- GET EXAMS FOR CLASS & SECTION ----------------
    @GetMapping("/class/{classLevel}/{section}")
    public ResponseEntity<List<Exam>> getExamsForClass(
            @PathVariable Integer classLevel,
            @PathVariable String section) {

        List<Exam> exams = examService.getExamsForClass(classLevel, section);
        return ResponseEntity.ok(exams);
    }

    // ---------------- UPDATE EXAM ----------------
    @PutMapping("/{examId}")
    public ResponseEntity<Exam> updateExam(
            @PathVariable ObjectId examId,
            @RequestBody Exam updatedExam) {

        Exam exam = examService.updateExam(examId, updatedExam);
        return ResponseEntity.ok(exam);
    }

    // ---------------- DELETE EXAM ----------------
    @DeleteMapping("/{examId}")
    public ResponseEntity<String> deleteExam(@PathVariable ObjectId examId) {
        examService.deleteExam(examId);
        return ResponseEntity.ok("Exam deleted successfully");
    }

    // ---------------- PUBLISH EXAM ----------------
    @PostMapping("/{examId}/publish")
    public ResponseEntity<Exam> publishExam(@PathVariable ObjectId examId) {
        Exam published = examService.publishExam(examId);
        return ResponseEntity.ok(published);
    }

    // ---------------- GET ALL EXAMS ----------------
    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

}
