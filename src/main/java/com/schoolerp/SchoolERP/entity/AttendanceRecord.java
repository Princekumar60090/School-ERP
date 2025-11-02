package com.schoolerp.SchoolERP.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document(collection = "attendance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecord {

    @Id
    private ObjectId id;

    @NotNull(message = "person id cannot be null")
    private ObjectId personId;

    @NotNull(message = "person type cannot be blank")
    private String personType;

    @NotNull(message = "date cannot be null")
    @PastOrPresent(message = "Attendance date cannot be in the future")
    private LocalDate date;

    @NotBlank(message = "status cannot be empty")
    private String status;

}
