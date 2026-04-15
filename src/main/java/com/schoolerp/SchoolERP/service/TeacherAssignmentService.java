package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.TeacherAssignment;
import com.schoolerp.SchoolERP.repository.TeacherAssignmentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherAssignmentService {

    @Autowired
    private TeacherAssignmentRepository assignmentRepo;

    // ----------------- ASSIGN TEACHER TO SUBJECT -----------------
    @Transactional
    public TeacherAssignment assignTeacher(TeacherAssignment assignment) {

        if (assignment == null) {
            throw new IllegalArgumentException("Assignment cannot be null");
        }

        if (assignment.getTeacherId() == null ||
                assignment.getSubjectId() == null ||
                assignment.getClassLevel() == null ||
                assignment.getSection() == null) {

            throw new IllegalArgumentException("All fields are required");
        }

        boolean exists = assignmentRepo
                .existsByTeacherIdAndSubjectIdAndClassLevelAndSection(
                        assignment.getTeacherId(),
                        assignment.getSubjectId(),
                        assignment.getClassLevel(),
                        assignment.getSection().trim().toUpperCase()
                );

        if (exists) {
            throw new IllegalStateException("Teacher is already assigned for this subject and class");
        }

        assignment.setSection(assignment.getSection().trim().toUpperCase());

        return assignmentRepo.save(assignment);
    }

    // ----------------- GET ASSIGNMENTS FOR A TEACHER -----------------
    @Transactional(readOnly = true)
    public List<TeacherAssignment> getAssignmentsForTeacher(ObjectId teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID is required");
        }
        return assignmentRepo.findByTeacherId(teacherId);
    }

    // ----------------- VERIFY TEACHER ACCESS (IMPORTANT!) -----------------
    @Transactional(readOnly = true)
    public boolean verifyTeacherAccess(ObjectId teacherId,
                                       ObjectId subjectId,
                                       Integer classLevel,
                                       String section) {

        if (teacherId == null || subjectId == null || classLevel == null || section == null) {
            throw new IllegalArgumentException("Invalid input for access check");
        }

        return assignmentRepo.existsByTeacherIdAndSubjectIdAndClassLevelAndSection(
                teacherId,
                subjectId,
                classLevel,
                section.trim().toUpperCase()
        );
    }
}
