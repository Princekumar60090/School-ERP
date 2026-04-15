package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.repository.TeacherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Save new teacher
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Get all
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Get by id
    public Optional<Teacher> getTeacherById(ObjectId id) {
        return teacherRepository.findById(id);
    }

    // Update (partial simple)
    public Teacher updateTeacher(ObjectId id, Teacher details) {
        Teacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (details.getName() != null) t.setName(details.getName());
        if (details.getSubject() != null) t.setSubject(details.getSubject());
        if (details.getPhone() != null) t.setPhone(details.getPhone());
        if (details.getEmail() != null) t.setEmail(details.getEmail());
        if (details.getSalary() > 0) t.setSalary(details.getSalary());
        if (details.getDateOfJoining() != null) t.setDateOfJoining(details.getDateOfJoining());
        // allow setting userId via update body if provided
        if (details.getUserId() != null) t.setUserId(details.getUserId());

        return teacherRepository.save(t);
    }

    // Delete
    public void deleteTeacherById(ObjectId id) {
        teacherRepository.deleteById(id);
    }

    // Link existing user id to teacher (explicit API)
    public Teacher linkUserToTeacher(ObjectId teacherId, ObjectId userId) {
        Teacher t = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        t.setUserId(userId);
        return teacherRepository.save(t);
    }

    // Get teachers by subject / optional helper
    public List<Teacher> findBySubject(String subject) {
        return teacherRepository.findBySubject(subject);
    }
}
