package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.FeePayment;
import com.schoolerp.SchoolERP.service.FeePaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feepayments")
@SecurityRequirement(name = "basicAuth")
public class FeePaymentController {
    @Autowired
    private FeePaymentService feePaymentService;


    @PostMapping
    public ResponseEntity<?> recordPayment(@Valid @RequestBody FeePayment feePayment) {
        try {

            FeePayment savedPayment = feePaymentService.recordPayment(feePayment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred while recording payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<FeePayment> getPaymentById(@PathVariable ObjectId id) {
        Optional<FeePayment> payment = feePaymentService.getPaymentById(id);
        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getPaymentsForStudent(@PathVariable ObjectId studentId) {
        try {
            List<FeePayment> payments = feePaymentService.getPaymentsForStudent(studentId);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/student/{studentId}/type/{feeType}")
    public ResponseEntity<?> getPaymentsForStudentByType(
            @PathVariable ObjectId studentId,
            @PathVariable String feeType) {
        try {
            List<FeePayment> payments = feePaymentService.getPaymentsForStudentByType(studentId, feeType);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
