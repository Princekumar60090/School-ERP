package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, ObjectId> {
  Optional<Student> findByRollNo(String rollNo);
    List<Student> findByGrade(int grade);
}
