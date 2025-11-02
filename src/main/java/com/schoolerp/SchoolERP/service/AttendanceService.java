package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.AttendanceRecord;
import com.schoolerp.SchoolERP.entity.SchoolClass;
import com.schoolerp.SchoolERP.entity.Staff;
import com.schoolerp.SchoolERP.entity.Teacher;
import com.schoolerp.SchoolERP.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;


   public AttendanceRecord markAttendance(AttendanceRecord attendanceRecord){
       validatePersonExists(attendanceRecord.getPersonId(),attendanceRecord.getPersonType());
       validateStatus(attendanceRecord.getStatus());

       Optional<AttendanceRecord> existingRecord = attendanceRecordRepository.findByPersonIdAndDate(
               attendanceRecord.getPersonId(),
               attendanceRecord.getDate()
       );

       if (existingRecord.isPresent()) {
           throw new IllegalStateException("Attendance for person " + attendanceRecord.getPersonId() +
                   " on date " + attendanceRecord.getDate() + " has already been marked.");
       }

       // Step 4: Agar sab theek hai, to naya record save karein
       return attendanceRecordRepository.save(attendanceRecord);
   }




    private void validatePersonExists(ObjectId personId, String personType ){
        boolean exists=false;
        if("STUDENT".equalsIgnoreCase(personType)){
            exists=studentRepository.existsById(personId);
        }
        else if("TEACHER".equalsIgnoreCase(personType)){
            exists=teacherRepository.existsById(personId);
        }
        else if("STAFF".equalsIgnoreCase(personType)){
            exists=staffRepository.existsById(personId);
        }
        else{
            throw new IllegalArgumentException("Invalid person type: " + personType);
        }

        if(!exists){
            throw new IllegalArgumentException("No " + personType + " found with id: " + personId);
        }
    }

    private void validateStatus(String status){
        if(!("PRESENT".equalsIgnoreCase(status) || "ABSENT".equalsIgnoreCase(status) || "LEAVE".equalsIgnoreCase(status))) {
            throw new IllegalArgumentException("Invalid attendance status: " + status + ". Allowed statuses are PRESENT, ABSENT, LEAVE.");
        }
    }






    // get all attendance record for a specific person
    public List<AttendanceRecord> getAttendanceForPerson(ObjectId personId){
       return attendanceRecordRepository.findByPersonId(personId);
    }


    // get all attendance record for a specific date
    public List<AttendanceRecord> getAttendanceByDate(LocalDate date){
       return attendanceRecordRepository.findByDate(date);
    }


    // get all attendance records for a specific date and person type

    public List<AttendanceRecord> getAttendanceByDateAndType(LocalDate date,String personType){
        if (!("STUDENT".equalsIgnoreCase(personType) || "TEACHER".equalsIgnoreCase(personType) || "STAFF".equalsIgnoreCase(personType))) {
            throw new IllegalArgumentException("Invalid person type: " + personType);
        }
        return attendanceRecordRepository.findByDateAndPersonType(date, personType);
    }


    // get attendance for all students of a specific class on a specific date

    public List<AttendanceRecord> getAttendanceForClassOnDate(ObjectId classId,LocalDate date){
        Optional<SchoolClass> classOptional = schoolClassRepository.findById(classId);
        if (!classOptional.isPresent()) {
            throw new IllegalStateException("Class not found with id: " + classId);
        }
        SchoolClass schoolClass = classOptional.get();

        // 2. Class se saare student IDs nikalo
        List<ObjectId> studentIds = schoolClass.getStudentIds();
        if (studentIds == null || studentIds.isEmpty()) {
            return new java.util.ArrayList<>(); // Agar class mein student nahi hain, to khaali list bhejo
        }

        // 3. Har student ke liye uss date ka attendance record dhoondho
        List<AttendanceRecord> classAttendance = new java.util.ArrayList<>();
        for (ObjectId studentId : studentIds) {
            Optional<AttendanceRecord> recordOptional = attendanceRecordRepository.findByPersonIdAndDate(studentId, date);
            // Agar record mila, to use list mein add karo
            recordOptional.ifPresent(classAttendance::add);
            // Agar record nahi mila, to iska matlab hai uss student ki attendance mark nahi hui hai uss din.
            // Hum yahan chahein to ek "NOT MARKED" record bana kar add kar sakte hain, ya ignore kar sakte hain. Abhi ignore karte hain.
        }

        // 4. Saare records ki list wapas bhejo
        return classAttendance;
    }


}


