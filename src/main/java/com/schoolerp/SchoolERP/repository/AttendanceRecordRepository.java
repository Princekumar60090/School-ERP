package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.AttendanceRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends MongoRepository<AttendanceRecord, ObjectId> {

    Optional<AttendanceRecord> findByPersonIdAndDate(ObjectId personId, LocalDate date);

    List<AttendanceRecord> findByPersonId(ObjectId personId);

    List<AttendanceRecord> findByDate(LocalDate date);

    List<AttendanceRecord> findByDateAndPersonType(LocalDate date,String personType);
}
