package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.StudentMarks;
import com.schoolerp.SchoolERP.service.StudentMarksService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@SecurityRequirement(name = "basicAuth")
public class StudentMarksController {

    @Autowired
    private StudentMarksService marksService;

    // ---------------- ADD MARKS ----------------
    @PostMapping
    public ResponseEntity<StudentMarks> addMarks(@RequestBody StudentMarks marks) {
        StudentMarks saved = marksService.addMarks(marks);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ---------------- UPDATE MARKS ----------------
    @PutMapping("/{marksId}")
    public ResponseEntity<StudentMarks> updateMarks(
            @PathVariable ObjectId marksId,
            @RequestBody StudentMarks updatedMarks) {

        StudentMarks updated = marksService.updateMarks(marksId, updatedMarks);
        return ResponseEntity.ok(updated);
    }

    // ---------------- GET MARKS FOR STUDENT IN ONE EXAM ----------------
    @GetMapping("/exam/{examId}/student/{studentId}")
    public ResponseEntity<List<StudentMarks>> getMarksForStudentInExam(
            @PathVariable ObjectId examId,
            @PathVariable ObjectId studentId) {

        List<StudentMarks> list = marksService.getStudentMarksForExam(examId, studentId);
        return ResponseEntity.ok(list);
    }

    // ---------------- GET SPECIFIC SUBJECT MARK ----------------
    @GetMapping("/exam/{examId}/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<StudentMarks> getSubjectMarks(
            @PathVariable ObjectId examId,
            @PathVariable ObjectId studentId,
            @PathVariable ObjectId subjectId) {

        StudentMarks marks =
                marksService.getStudentSubjectMarks(examId, studentId, subjectId);

        return ResponseEntity.ok(marks);
    }

    // ---------------- GET ALL MARKS FOR AN EXAM ----------------
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<StudentMarks>> getAllMarksForExam(
            @PathVariable ObjectId examId) {

        List<StudentMarks> list = marksService.getAllMarksForExam(examId);
        return ResponseEntity.ok(list);
    }

    // ---------------- GET FULL REPORT OF STUDENT ----------------
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentMarks>> getFullReportOfStudent(
            @PathVariable ObjectId studentId) {

        List<StudentMarks> list = marksService.getFullReportForStudent(studentId);
        return ResponseEntity.ok(list);
    }
}
