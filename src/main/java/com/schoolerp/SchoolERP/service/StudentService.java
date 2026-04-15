package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Student;
import com.schoolerp.SchoolERP.repository.StudentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // CREATE
    public void saveStudent(Student student){
        // ⭐ If userId is provided, it will be saved automatically
        studentRepository.save(student);
    }

    // GET ALL
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    // GET BY GRADE
    public List<Student> getStudentsByGrade(int grade) {
        return studentRepository.findByGrade(grade);
    }

    // GET BY ID
    public Optional<Student> getStudentById(ObjectId id){
        return studentRepository.findById(id);
    }

    // DELETE
    public void deleteStudentById(ObjectId id){
        studentRepository.deleteById(id);
    }

    // UPDATE
    public Student updateStudent(ObjectId id, Student newStudent){
        Optional<Student> studentOptional = studentRepository.findById(id);

        if(studentOptional.isPresent()){
            Student existingStudent = studentOptional.get();

            if (newStudent.getName() != null)
                existingStudent.setName(newStudent.getName());

            if (newStudent.getRollNo() != null)
                existingStudent.setRollNo(newStudent.getRollNo());

            if (newStudent.getFatherName() != null)
                existingStudent.setFatherName(newStudent.getFatherName());

            if (newStudent.getMotherName() != null)
                existingStudent.setMotherName(newStudent.getMotherName());

            if (newStudent.getGrade() != 0)
                existingStudent.setGrade(newStudent.getGrade());

            if (newStudent.getPhone() != null)
                existingStudent.setPhone(newStudent.getPhone());

            // ⭐ NEW: OPTIONAL userId update (only if provided)
            if (newStudent.getUserId() != null)
                existingStudent.setUserId(newStudent.getUserId());

            // SAVE
            return studentRepository.save(existingStudent);
        }

        return null;
    }

    // FIND BY ROLL NO
    public Optional<Student> getStudentByRollNo(String rollNo){
        return studentRepository.findByRollNo(rollNo);
    }

}
