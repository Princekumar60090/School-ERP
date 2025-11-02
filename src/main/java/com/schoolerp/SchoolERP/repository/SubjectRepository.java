package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Subject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, ObjectId> {
   Optional<Subject> findBySubjectCode(String subjectCode);
}
