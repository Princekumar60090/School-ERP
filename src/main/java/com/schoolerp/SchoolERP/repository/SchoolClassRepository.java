package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.SchoolClass;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SchoolClassRepository extends MongoRepository<SchoolClass, ObjectId> {

    Optional<SchoolClass> findByGradeLevelAndSection(Integer gradeLevel, String section);
}
