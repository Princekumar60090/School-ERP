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

    public Staff saveStaff(Staff staff){
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(ObjectId id){
        return staffRepository.findById(id);
    }

    public Staff updateStaff(ObjectId id,Staff staffDetails){
        Optional<Staff> staffOptional=staffRepository.findById(id);
        if(staffOptional.isPresent()){
            Staff existingStaff=staffOptional.get();
            existingStaff.setName(staffDetails.getName());
            existingStaff.setRole(staffDetails.getRole());
            existingStaff.setPhone(staffDetails.getPhone());
            existingStaff.setEmail(staffDetails.getEmail());
            existingStaff.setSalary(staffDetails.getSalary());
            existingStaff.setDateOfJoining(staffDetails.getDateOfJoining());
            return staffRepository.save(existingStaff);
        }
        return null;
    }

    public void deleteStaffById(ObjectId id){
        if(!staffRepository.existsById(id)){
            throw new IllegalStateException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }
}
