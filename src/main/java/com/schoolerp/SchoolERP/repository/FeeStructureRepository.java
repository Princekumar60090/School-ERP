package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.FeeStructure;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeStructureRepository extends MongoRepository<FeeStructure, ObjectId> {
    List<FeeStructure> findByGradeLevel(Integer gradeLevel);

    Optional<FeeStructure> findByGradeLevelAndFeeType(Integer gradeLevel, String feeType);
}
