package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student_marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarks {

    @Id
    private ObjectId id;

    @NotNull(message ="Exam ID cannot be null" )
    private ObjectId examId;

    @NotNull(message = "Subject ID cannot be null" )
    private ObjectId subjectId;

    @NotNull(message = "Student ID is required")
    private ObjectId studentId;

    @NotNull(message = "Teacher ID cannot be null")
    private ObjectId teacherId;

    @NotNull(message = "Marks obtained cannot be null")
    @Min(value = 0,message = "Marks cannot be negative")
    private Integer marksObtained;

    @NotNull(message = "Passing marks cannot be null")
    @Min(value = 0, message = "Passing marks cannot be negative")
    private Integer passingMarks;

    private String remarks;

    private boolean passed;
}
