package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.SalaryPayment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryPaymentRepository extends MongoRepository<SalaryPayment, ObjectId> {
    List<SalaryPayment> findByPersonId(ObjectId personId);


    Optional<SalaryPayment> findByPersonIdAndSalaryMonthAndSalaryYear(ObjectId personId, Month salaryMonth, Integer salaryYear);

    List<SalaryPayment> findBySalaryMonthAndSalaryYear(Month salaryMonth, Integer salaryYear);
}
