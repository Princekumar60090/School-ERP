package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.SchoolClass;
import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.repository.SchoolClassRepository;
import com.schoolerp.SchoolERP.repository.TeacherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolClassService {
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public SchoolClass createSchoolClass(SchoolClass schoolClass) {
        Optional<SchoolClass> existingClass = schoolClassRepository.findByGradeLevelAndSection(
                schoolClass.getGradeLevel(),
                schoolClass.getSection()
        );

        if (existingClass.isPresent()) {
            throw new IllegalStateException("Class with Grade " + schoolClass.getGradeLevel() +
                    " and Section '" + schoolClass.getSection() + "' already exists.");
        }

        if (schoolClass.getStudentIds() == null) {
            schoolClass.setStudentIds(new java.util.ArrayList<>());
        }
        if (schoolClass.getSubjectIds() == null) {
            schoolClass.setSubjectIds(new java.util.ArrayList<>());
        }

        return schoolClassRepository.save(schoolClass);
    }


    public List<SchoolClass> getAllSchoolClasses(){
        return schoolClassRepository.findAll();
    }

    public Optional<SchoolClass> getSchoolClassById(ObjectId id){
        return schoolClassRepository.findById(id);
    }


    public SchoolClass addStudentToClass(ObjectId classId, ObjectId studentId) {

        Optional<SchoolClass> classOptional = schoolClassRepository.findById(classId);
        if (!classOptional.isPresent()) {
            throw new IllegalStateException("Class not found with id: " + classId);
        }

        SchoolClass schoolClass = classOptional.get();

        // Check karo ki student pehle se add to nahi hai
        if (schoolClass.getStudentIds().contains(studentId)) {
            throw new IllegalStateException("Student is already enrolled in this class.");
        }

        schoolClass.getStudentIds().add(studentId);
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass addSubjectToClass(ObjectId classId, ObjectId subjectId) {
        Optional<SchoolClass> classOptional = schoolClassRepository.findById(classId);
        if (!classOptional.isPresent()) {
            throw new IllegalStateException("Class not found with id: " + classId);
        }

        SchoolClass schoolClass = classOptional.get();

        if (schoolClass.getSubjectIds().contains(subjectId)) {
            throw new IllegalStateException("Subject is already assigned to this class.");
        }

        schoolClass.getSubjectIds().add(subjectId);
        return schoolClassRepository.save(schoolClass);
    }


    // 6. Ek Class ke Class Teacher ki poori Details Get karna
    public Teacher getClassTeacherDetails(ObjectId classId) {
        // 1. Pehle class ko dhoondho
        Optional<SchoolClass> classOptional = schoolClassRepository.findById(classId);
        if (!classOptional.isPresent()) {
            throw new IllegalStateException("Class not found with id: "+ classId);
        }

        SchoolClass schoolClass = classOptional.get();

        // 2. Class se teacher ki ID nikalo
        ObjectId teacherId = schoolClass.getClassTeacherId();
        if (teacherId == null) {
            throw new IllegalStateException("This class does not have a Class Teacher assigned.");
        }

        // 3. Uss ID ka istemaal karke 'TeacherRepository' se poore teacher ko dhoondho
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (!teacherOptional.isPresent()) {
            throw new IllegalStateException("Assigned Class Teacher not found with id: " + teacherId);
        }

        // 4. Poora Teacher object wapas bhejo
        return teacherOptional.get();
    }

    // 5. Ek Teacher ko Class Teacher assign karna
    public SchoolClass assignClassTeacher(ObjectId classId, ObjectId teacherId) {
        // Pehle class ko dhoondho
        Optional<SchoolClass> classOptional = schoolClassRepository.findById(classId);
        if (!classOptional.isPresent()) {
            throw new IllegalStateException("Class not found with id: " + classId);
        }

        SchoolClass schoolClass = classOptional.get();

        // **Yahan Hum Logic Add Kar Sakte Hain**
        // Hum check kar sakte hain ki jo teacherId aayi hai, woh 'teachers' collection mein hai ya nahi
        // Lekin abhi ke liye, hum ise simple rakhenge.

        // Teacher ki ID ko 'classTeacherId' field mein set karo aur save karo
        schoolClass.setClassTeacherId(teacherId);
        return schoolClassRepository.save(schoolClass);
    }



    // 9. Ek poori class ko delete karna
    public void deleteSchoolClass(ObjectId classId) {
        // Check karo ki class hai ya nahi, taaki galat ID par error aaye
        if (!schoolClassRepository.existsById(classId)) {
            throw new IllegalStateException("Class not found with id: " + classId);
        }
        // Class ko delete karo
        schoolClassRepository.deleteById(classId);
    }


}
