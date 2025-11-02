package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Staff;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends MongoRepository<Staff, ObjectId> {


}
