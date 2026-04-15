package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.AttendanceRecord;
import com.schoolerp.SchoolERP.service.AttendanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@SecurityRequirement(name = "basicAuth")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;


    @PostMapping
    public ResponseEntity<?> markAttendance(@Valid @RequestBody AttendanceRecord attendanceRecord) {
        try {
            // Service method ko call karein
            AttendanceRecord savedRecord = attendanceService.markAttendance(attendanceRecord);
            return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);

        } catch (IllegalStateException e) {
            // Agar attendance pehle se mark hai (duplicate)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (IllegalArgumentException e) {
            // Agar personId, personType ya status invalid hai
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // Koi aur unexpected error
            return new ResponseEntity<>("An error occurred while marking attendance: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    // 2. Ek specific person (student/teacher/staff) ki saari attendance history get karein
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<AttendanceRecord>> getAttendanceForPerson(@PathVariable ObjectId personId) {
        List<AttendanceRecord> records = attendanceService.getAttendanceForPerson(personId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 3. Ek specific date ki saari attendance records get karein
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceRecord>> getAttendanceByDate(@PathVariable LocalDate date) {
        List<AttendanceRecord> records = attendanceService.getAttendanceByDate(date);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 4. Ek specific date par specific type ke logon ki attendance get karein
    @GetMapping("/date/{date}/type/{personType}")
    public ResponseEntity<?> getAttendanceByDateAndType(@PathVariable LocalDate date, @PathVariable String personType) {
        try {
            List<AttendanceRecord> records = attendanceService.getAttendanceByDateAndType(date, personType);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 5. Ek specific class ke sabhi students ki attendance ek specific date par get karein
    @GetMapping("/class/{classId}/date/{date}")
    public ResponseEntity<?> getAttendanceForClassOnDate(@PathVariable ObjectId classId, @PathVariable LocalDate date) {
        try {
            List<AttendanceRecord> records = attendanceService.getAttendanceForClassOnDate(classId, date);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (IllegalStateException e) { // Class not found error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
