package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.SalaryPayment;
import com.schoolerp.SchoolERP.entity.Staff;
import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.repository.SalaryPaymentRepository;
import com.schoolerp.SchoolERP.repository.StaffRepository;
import com.schoolerp.SchoolERP.repository.TeacherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StaffRepository staffRepository;



    public SalaryPayment paySalary(SalaryPayment salaryPayment) {

        double baseSalary = validatePersonAndGetSalary(
                salaryPayment.getPersonId(),
                salaryPayment.getPersonType()
        );

        Optional<SalaryPayment> existingPayment = salaryPaymentRepository.findByPersonIdAndSalaryMonthAndSalaryYear(
                salaryPayment.getPersonId(),
                salaryPayment.getSalaryMonth(),
                salaryPayment.getSalaryYear()
        );

        if (existingPayment.isPresent()) {
            throw new IllegalStateException("Salary for person " + salaryPayment.getPersonId() +
                    " for " + salaryPayment.getSalaryMonth() + " " + salaryPayment.getSalaryYear() +
                    " has already been paid.");
        }


        salaryPayment.setAmount(baseSalary);

        return salaryPaymentRepository.save(salaryPayment);
    }


    private double validatePersonAndGetSalary(ObjectId personId, String personType) {
        if ("TEACHER".equalsIgnoreCase(personType)) {
            Optional<Teacher> teacher = teacherRepository.findById(personId);
            if (teacher.isPresent()) {
                if (teacher.get().getSalary() <= 0) {
                    throw new IllegalStateException("Salary not set or is invalid for this teacher.");
                }
                return teacher.get().getSalary();
            } else {
                throw new IllegalArgumentException("No TEACHER found with id: " + personId);
            }
        } else if ("STAFF".equalsIgnoreCase(personType)) {
            Optional<Staff> staff = staffRepository.findById(personId);
            if (staff.isPresent()) {
                if (staff.get().getSalary() == null || staff.get().getSalary() < 0) {
                    throw new IllegalStateException("Salary is not set or is invalid (negative) for this staff.");
                }
                return staff.get().getSalary();
            } else {
                throw new IllegalArgumentException("No STAFF found with id: " + personId);
            }
        } else {
            throw new IllegalArgumentException("Invalid person type: " + personType + ". Must be TEACHER or STAFF.");
        }
    }


    public List<SalaryPayment> getSalaryHistoryForPerson(ObjectId personId) {
        return salaryPaymentRepository.findByPersonId(personId);
    }


    public List<SalaryPayment> getPaymentsByMonthAndYear(Month month, Integer year) {
        return salaryPaymentRepository.findBySalaryMonthAndSalaryYear(month, year);
    }



    public Optional<SalaryPayment> getSalaryPaymentById(ObjectId id) {
        return salaryPaymentRepository.findById(id);
    }


    public void deleteSalaryPayment(ObjectId id) {
        if (!salaryPaymentRepository.existsById(id)) {
            throw new IllegalStateException("Salary payment record not found with id: " + id);
        }
        salaryPaymentRepository.deleteById(id);
    }
}
