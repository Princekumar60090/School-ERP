package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Teacher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, ObjectId> {

}
