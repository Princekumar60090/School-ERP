package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.SalaryPayment;
import com.schoolerp.SchoolERP.service.SalaryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salary")
@SecurityRequirement(name = "basicAuth")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;


    @PostMapping
    public ResponseEntity<?> paySalary(@Valid @RequestBody SalaryPayment salaryPayment) {
        try {
            SalaryPayment savedPayment = salaryService.paySalary(salaryPayment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);

        } catch (IllegalStateException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred while paying salary: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/person/{personId}")
    public ResponseEntity<List<SalaryPayment>> getSalaryHistoryForPerson(@PathVariable ObjectId personId) {
        List<SalaryPayment> history = salaryService.getSalaryHistoryForPerson(personId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }


    @GetMapping("/report/{year}/{month}")
    public ResponseEntity<List<SalaryPayment>> getPaymentsByMonthAndYear(
            @PathVariable Integer year,
            @PathVariable Month month) {

        List<SalaryPayment> payments = salaryService.getPaymentsByMonthAndYear(month, year);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SalaryPayment> getSalaryPaymentById(@PathVariable ObjectId id) {
        Optional<SalaryPayment> payment = salaryService.getSalaryPaymentById(id);
        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalaryPayment(@PathVariable ObjectId id) {
        try {
            salaryService.deleteSalaryPayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
