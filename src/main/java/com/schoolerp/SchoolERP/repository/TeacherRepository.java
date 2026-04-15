package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Teacher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeacherRepository extends MongoRepository<Teacher, ObjectId> {
    List<Teacher> findBySubject(String subject);
}
