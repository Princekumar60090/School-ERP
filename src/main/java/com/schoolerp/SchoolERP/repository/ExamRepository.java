package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Exam;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends MongoRepository<Exam, ObjectId> {

    // Get exams for a specific class & section
    List<Exam> findByClassLevelAndSection(Integer classLevel, String section);

    // Check if a similar exam exists (optional but useful)
    boolean existsByNameAndClassLevelAndSection(String name, Integer classLevel, String section);
}
