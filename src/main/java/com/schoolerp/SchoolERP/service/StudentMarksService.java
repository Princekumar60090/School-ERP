package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.StudentMarks;
import com.schoolerp.SchoolERP.repository.StudentMarksRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentMarksService {

    @Autowired
    private StudentMarksRepository marksRepository;

    // ---------------- ADD MARKS ----------------
    @Transactional
    public StudentMarks addMarks(StudentMarks marks) {

        if (marks == null) {
            throw new IllegalArgumentException("Marks data cannot be null");
        }

        if (marks.getExamId() == null ||
                marks.getStudentId() == null ||
                marks.getSubjectId() == null) {

            throw new IllegalArgumentException("Exam ID, Student ID and Subject ID are required");
        }

        // check duplicate marks
        boolean exists = marksRepository
                .findByExamIdAndSubjectIdAndStudentId(
                        marks.getExamId(),
                        marks.getSubjectId(),
                        marks.getStudentId()
                ).isPresent();

        if (exists) {
            throw new IllegalStateException("Marks already entered for this student and subject");
        }

        if (marks.getMarksObtained() == null || marks.getMarksObtained() < 0) {
            throw new IllegalArgumentException("Marks cannot be negative");
        }

        // simple pass/fail calculation (optional)
        if (marks.getPassingMarks() != null) {
            marks.setPassed(marks.getMarksObtained() >= marks.getPassingMarks());
        }

        return marksRepository.save(marks);
    }


    // ---------------- UPDATE MARKS ----------------
    @Transactional
    public StudentMarks updateMarks(ObjectId marksId, StudentMarks updated) {

        StudentMarks existing = marksRepository.findById(marksId)
                .orElseThrow(() ->
                        new IllegalArgumentException("No marks found for ID: " + marksId));

        if (updated.getMarksObtained() != null && updated.getMarksObtained() >= 0) {
            existing.setMarksObtained(updated.getMarksObtained());
        }

        if (updated.getRemarks() != null) {
            existing.setRemarks(updated.getRemarks());
        }

        // recalculate pass/fail if passing marks exist
        if (existing.getPassingMarks() != null) {
            existing.setPassed(existing.getMarksObtained() >= existing.getPassingMarks());
        }

        return marksRepository.save(existing);
    }


    // ---------------- GET MARKS FOR ONE STUDENT IN ONE EXAM ----------------
    @Transactional(readOnly = true)
    public List<StudentMarks> getStudentMarksForExam(ObjectId examId, ObjectId studentId) {

        if (examId == null || studentId == null) {
            throw new IllegalArgumentException("Exam ID and Student ID are required");
        }

        return marksRepository.findByExamIdAndStudentId(examId, studentId);
    }


    // ---------------- GET SINGLE SUBJECT MARK ----------------
    @Transactional(readOnly = true)
    public StudentMarks getStudentSubjectMarks(ObjectId examId,
                                               ObjectId studentId,
                                               ObjectId subjectId) {

        if (examId == null || studentId == null || subjectId == null) {
            throw new IllegalArgumentException("Invalid details provided");
        }

        return marksRepository
                .findByExamIdAndSubjectIdAndStudentId(examId, subjectId, studentId)
                .orElseThrow(() -> new IllegalArgumentException("No marks found for given data"));
    }


    // ---------------- GET ALL MARKS FOR AN EXAM ----------------
    @Transactional(readOnly = true)
    public List<StudentMarks> getAllMarksForExam(ObjectId examId) {

        if (examId == null) {
            throw new IllegalArgumentException("Exam ID is required");
        }

        return marksRepository.findByExamId(examId);
    }


    // ---------------- GET FULL REPORT OF A STUDENT ----------------
    @Transactional(readOnly = true)
    public List<StudentMarks> getFullReportForStudent(ObjectId studentId) {

        if (studentId == null) {
            throw new IllegalArgumentException("Student ID is required");
        }

        return marksRepository.findByStudentId(studentId);
    }
}
