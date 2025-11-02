package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.repository.TeacherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers(){
         return teacherRepository.findAll();
    }

    public Teacher updateTeacher(ObjectId id, Teacher newTeacherDetails) {
        // Find the teacher by their ID
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        // Check if the teacher exists
        if (teacherOptional.isPresent()) {
            // Get the existing teacher object
            Teacher existingTeacher = teacherOptional.get();

            // Update the fields with the new details
            existingTeacher.setName(newTeacherDetails.getName());
            existingTeacher.setSubject(newTeacherDetails.getSubject());
            existingTeacher.setPhone(newTeacherDetails.getPhone());
            existingTeacher.setEmail(newTeacherDetails.getEmail());
            existingTeacher.setSalary(newTeacherDetails.getSalary());
            existingTeacher.setDateOfJoining(newTeacherDetails.getDateOfJoining());

            // Save the updated teacher and return it
            return teacherRepository.save(existingTeacher);
        } else {
            // If the teacher is not found, return null
            return null;
        }
    }

    public Optional<Teacher> getTeacherById(ObjectId id){
        return teacherRepository.findById(id);
    }

    public void deleteTeacherById(ObjectId id){
        teacherRepository.deleteById(id);
    }
}
