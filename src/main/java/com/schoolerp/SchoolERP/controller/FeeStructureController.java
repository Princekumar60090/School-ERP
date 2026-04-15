package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.FeeStructure;
import com.schoolerp.SchoolERP.service.FeeStructureService;
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
@RequestMapping("/api/feestructures")
@SecurityRequirement(name = "basicAuth")
public class FeeStructureController {
    @Autowired
    private FeeStructureService feeStructureService;


    @PostMapping
    public ResponseEntity<?> addFeeStructure(@Valid @RequestBody FeeStructure feeStructure) {
        try {
            FeeStructure newFee = feeStructureService.addFeeStructure(feeStructure);
            return new ResponseEntity<>(newFee, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<FeeStructure> getAllFeeStructures() {
        return feeStructureService.getAllFeeStructures();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FeeStructure> getFeeStructureById(@PathVariable ObjectId id) {
        Optional<FeeStructure> fee = feeStructureService.getFeeStructureById(id);
        if (fee.isPresent()) {
            return new ResponseEntity<>(fee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/grade/{gradeLevel}")
    public ResponseEntity<List<FeeStructure>> getFeeStructuresByGrade(@PathVariable Integer gradeLevel) {
        List<FeeStructure> fees = feeStructureService.getFeeStructuresByGrade(gradeLevel);
        return new ResponseEntity<>(fees, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeeStructure(@PathVariable ObjectId id, @Valid @RequestBody FeeStructure feeDetails) {
        try {
            FeeStructure updatedFee = feeStructureService.updateFeeStructure(id, feeDetails);
            if (updatedFee != null) {
                return new ResponseEntity<>(updatedFee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Fee structure not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalStateException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeeStructure(@PathVariable ObjectId id) {
        try {
            feeStructureService.deleteFeeStructure(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
