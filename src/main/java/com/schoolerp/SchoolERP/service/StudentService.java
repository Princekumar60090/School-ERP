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

    public void saveStudent(Student student){
        studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByGrade(int grade) {
        return studentRepository.findByGrade(grade);
    }

    public Optional<Student> getStudentById(ObjectId id){
        return studentRepository.findById(id);
    }

    public void deleteStudentById(ObjectId id){
        studentRepository.deleteById(id);
    }

    public Student updateStudent(ObjectId id, Student newStudent){
        Optional<Student>studentOptional= studentRepository.findById(id);
        if(studentOptional.isPresent()){
            Student existingStudent = studentOptional.get();
            existingStudent.setName(newStudent.getName());
            existingStudent.setRollNo(newStudent.getRollNo());
            existingStudent.setFatherName(newStudent.getFatherName());
            existingStudent.setMotherName(newStudent.getMotherName());
            existingStudent.setGrade(newStudent.getGrade());
            existingStudent.setPhone(newStudent.getPhone());

            // 4. Save the updated student object back to the database
            return studentRepository.save(existingStudent);
        }
        else {
            return null;
        }
    }



    public Optional<Student> getStudentByRollNo(String rollNo){
        return studentRepository.findByRollNo(rollNo);
    }


}
