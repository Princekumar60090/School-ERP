package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.TeacherAssignment;
import com.schoolerp.SchoolERP.service.TeacherAssignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher-assignments")
@SecurityRequirement(name = "basicAuth")
public class TeacherAssignmentController {

    @Autowired
    private TeacherAssignmentService assignmentService;

    // ---------------- ASSIGN A TEACHER ----------------
    @PostMapping
    public ResponseEntity<TeacherAssignment> assignTeacher(@RequestBody TeacherAssignment assignment) {
        TeacherAssignment saved = assignmentService.assignTeacher(assignment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ---------------- GET ASSIGNMENTS FOR A TEACHER ----------------
    @GetMapping("/{teacherId}")
    public ResponseEntity<List<TeacherAssignment>> getAssignmentsForTeacher(
            @PathVariable ObjectId teacherId) {

        List<TeacherAssignment> assignments = assignmentService.getAssignmentsForTeacher(teacherId);
        return ResponseEntity.ok(assignments);
    }

    // ---------------- VERIFY TEACHER ACCESS (OPTIONAL) ----------------
    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyTeacherAccess(
            @RequestParam ObjectId teacherId,
            @RequestParam ObjectId subjectId,
            @RequestParam Integer classLevel,
            @RequestParam String section) {

        boolean allowed = assignmentService.verifyTeacherAccess(
                teacherId, subjectId, classLevel, section);

        return ResponseEntity.ok(allowed);
    }

}
