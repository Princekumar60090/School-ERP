package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.TeacherAssignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAssignmentRepository extends MongoRepository<TeacherAssignment, ObjectId> {
    // Get all assignments for a teacher
    List<TeacherAssignment> findByTeacherId(ObjectId teacherId);
    // Check if teacher teaches this subject in this class-section
    boolean existsByTeacherIdAndSubjectIdAndClassLevelAndSection(
            ObjectId teacherId, ObjectId subjectId, Integer classLevel, String section);

    TeacherAssignment findByTeacherIdAndSubjectIdAndClassLevelAndSection(
            ObjectId teacherId, ObjectId subjectId, Integer classLevel, String section
    );
}
