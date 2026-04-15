package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.FeeStructure;
import com.schoolerp.SchoolERP.repository.FeeStructureRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeStructureService {

    @Autowired
    private FeeStructureRepository feeStructureRepository;


    public FeeStructure addFeeStructure(FeeStructure feeStructure) {
        Optional<FeeStructure> existingFee = feeStructureRepository.findByGradeLevelAndFeeType(
                feeStructure.getGradeLevel(),
                feeStructure.getFeeType()
        );

        if (existingFee.isPresent()) {
            throw new IllegalStateException("Fee type '" + feeStructure.getFeeType() +
                    "' already exists for Grade " + feeStructure.getGradeLevel());
        }

        return feeStructureRepository.save(feeStructure);
    }


    public List<FeeStructure> getAllFeeStructures() {
        return feeStructureRepository.findAll();
    }

    public Optional<FeeStructure> getFeeStructureById(ObjectId id) {
        return feeStructureRepository.findById(id);
    }


    public List<FeeStructure> getFeeStructuresByGrade(Integer gradeLevel) {
        return feeStructureRepository.findByGradeLevel(gradeLevel);
    }


    public FeeStructure updateFeeStructure(ObjectId id, FeeStructure feeDetails) {
        Optional<FeeStructure> feeOptional = feeStructureRepository.findById(id);
        if (feeOptional.isPresent()) {
            FeeStructure existingFee = feeOptional.get();


            Optional<FeeStructure> duplicateCheck = feeStructureRepository.findByGradeLevelAndFeeType(
                    feeDetails.getGradeLevel(), feeDetails.getFeeType()
            );

            if (duplicateCheck.isPresent() && !duplicateCheck.get().getId().equals(id)) {
                throw new IllegalStateException("Another fee structure with this grade and type already exists.");
            }

            existingFee.setGradeLevel(feeDetails.getGradeLevel());
            existingFee.setFeeType(feeDetails.getFeeType());
            existingFee.setAmount(feeDetails.getAmount());
            existingFee.setDescription(feeDetails.getDescription());
            return feeStructureRepository.save(existingFee);
        }
        return null;
    }


    public void deleteFeeStructure(ObjectId id) {
        if (!feeStructureRepository.existsById(id)) {
            throw new IllegalStateException("Fee structure not found with id: " + id);
        }
        feeStructureRepository.deleteById(id);
    }
}
