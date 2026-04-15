package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Staff;
import com.schoolerp.SchoolERP.repository.StaffRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // CREATE
    public Staff saveStaff(Staff staff){
        // ⭐ userId will be saved automatically if provided
        return staffRepository.save(staff);
    }

    // GET ALL
    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    // GET BY ID
    public Optional<Staff> getStaffById(ObjectId id){
        return staffRepository.findById(id);
    }

    // UPDATE
    public Staff updateStaff(ObjectId id, Staff staffDetails){
        Optional<Staff> staffOptional = staffRepository.findById(id);

        if(staffOptional.isPresent()){
            Staff existingStaff = staffOptional.get();

            if (staffDetails.getName() != null)
                existingStaff.setName(staffDetails.getName());

            if (staffDetails.getRole() != null)
                existingStaff.setRole(staffDetails.getRole());

            if (staffDetails.getPhone() != null)
                existingStaff.setPhone(staffDetails.getPhone());

            if (staffDetails.getEmail() != null)
                existingStaff.setEmail(staffDetails.getEmail());

            if (staffDetails.getSalary() > 0)
                existingStaff.setSalary(staffDetails.getSalary());

            if (staffDetails.getDateOfJoining() != null)
                existingStaff.setDateOfJoining(staffDetails.getDateOfJoining());

            // ⭐ NEW: add userId update support (optional)
            if (staffDetails.getUserId() != null)
                existingStaff.setUserId(staffDetails.getUserId());

            return staffRepository.save(existingStaff);
        }
        return null;
    }

    // DELETE
    public void deleteStaffById(ObjectId id){
        if(!staffRepository.existsById(id)){
            throw new IllegalStateException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }
}
