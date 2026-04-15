package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.StudentMarks;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentMarksRepository extends MongoRepository<StudentMarks, ObjectId> {

    Optional<StudentMarks> findByExamIdAndSubjectIdAndStudentId(
            ObjectId examId,
            ObjectId subjectId,
            ObjectId studentId
    );

    List<StudentMarks> findByExamIdAndStudentId(ObjectId examId, ObjectId studentId);

    List<StudentMarks> findByExamIdAndSubjectId(ObjectId examId, ObjectId subjectId);

    // REQUIRED for service
    List<StudentMarks> findByExamId(ObjectId examId);

    // REQUIRED for service
    List<StudentMarks> findByStudentId(ObjectId studentId);
}
