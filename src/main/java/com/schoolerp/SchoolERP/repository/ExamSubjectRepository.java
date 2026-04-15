package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.ExamSubject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamSubjectRepository extends MongoRepository<ExamSubject, ObjectId> {
    // Get all subjects belonging to a specific exam
    List<ExamSubject> findByExamId(ObjectId examId);

    // Check if a subject is already assigned to this exam
    boolean existsByExamIdAndSubjectId(ObjectId examId, ObjectId subjectId);

    // Find subject row for verifying teacher’s permission
    ExamSubject findByExamIdAndSubjectId(ObjectId examId, ObjectId subjectId);
}
