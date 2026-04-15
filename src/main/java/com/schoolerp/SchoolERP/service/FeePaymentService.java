package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.FeePayment;
import com.schoolerp.SchoolERP.repository.FeePaymentRepository;
import com.schoolerp.SchoolERP.repository.FeeStructureRepository;
import com.schoolerp.SchoolERP.repository.StudentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class FeePaymentService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    public FeePayment recordPayment(FeePayment feePayment) {


        if (!studentRepository.existsById(feePayment.getStudentId())) {
            throw new IllegalArgumentException("Student not found with id: " + feePayment.getStudentId());
        }


        if (feePayment.getFeeType() != null) {
            String type = feePayment.getFeeType().toLowerCase();
            if (type.contains("tution") || type.contains("tuition")) {
                feePayment.setFeeType("TUITION_FEE");
            }
        }

        if ("TUITION_FEE".equalsIgnoreCase(feePayment.getFeeType()) &&
                feePayment.getPaymentMonth() != null && feePayment.getPaymentYear() != null) {

            Optional<FeePayment> existingPayment = feePaymentRepository.findByStudentIdAndFeeTypeAndPaymentMonthAndPaymentYear(
                    feePayment.getStudentId(),
                    feePayment.getFeeType(),
                    feePayment.getPaymentMonth(),
                    feePayment.getPaymentYear()
            );

            if (existingPayment.isPresent()) {
                throw new IllegalStateException("Tuition fee for " + feePayment.getPaymentMonth() + " " +
                        feePayment.getPaymentYear() + " has already been paid for this student.");
            }
        }

        return feePaymentRepository.save(feePayment);
    }


    public List<FeePayment> getPaymentsForStudent(ObjectId studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }
        return feePaymentRepository.findByStudentId(studentId);
    }

    public List<FeePayment> getPaymentsForStudentByType(ObjectId studentId, String feeType) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }
        return feePaymentRepository.findByStudentIdAndFeeType(studentId, feeType);
    }

    public Optional<FeePayment> getPaymentById(ObjectId id) {
        return feePaymentRepository.findById(id);
    }
}
