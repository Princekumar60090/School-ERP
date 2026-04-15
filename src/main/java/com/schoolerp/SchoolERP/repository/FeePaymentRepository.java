package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.FeePayment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeePaymentRepository extends MongoRepository<FeePayment, ObjectId> {

    List<FeePayment> findByStudentId(ObjectId studentId);


    List<FeePayment> findByStudentIdAndFeeType(ObjectId studentId, String feeType);


    Optional<FeePayment> findByStudentIdAndFeeTypeAndPaymentMonthAndPaymentYear(
            ObjectId studentId, String feeType, Month paymentMonth, Integer paymentYear);
}
