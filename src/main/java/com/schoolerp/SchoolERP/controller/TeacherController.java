package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.service.TeacherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@SecurityRequirement(name = "basicAuth")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Create teacher (admin or authenticated)
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher saved = teacherService.saveTeacher(teacher);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    // Get one
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getOne(@PathVariable String id) {
        return teacherService.getTeacherById(new ObjectId(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable String id, @RequestBody Teacher details) {
        Teacher updated = teacherService.updateTeacher(new ObjectId(id), details);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        teacherService.deleteTeacherById(new ObjectId(id));
        return ResponseEntity.ok("Deleted");
    }

    // NEW: Link an existing user to this teacher
    @PutMapping("/{teacherId}/link-user/{userId}")
    public ResponseEntity<Teacher> linkUser(
            @PathVariable String teacherId,
            @PathVariable String userId) {

        Teacher result = teacherService.linkUserToTeacher(new ObjectId(teacherId), new ObjectId(userId));
        return ResponseEntity.ok(result);
    }
}
