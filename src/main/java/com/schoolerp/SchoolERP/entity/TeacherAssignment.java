package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teacher_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAssignment {

    @Id
    private ObjectId id;

    @NotNull(message = "Teacher ID is required")
    private ObjectId teacherId;

    @NotNull(message = "Subject ID is required")
    private ObjectId subjectId;

    @NotNull(message = "Class level cannot be null")
    @Min(value = 1, message = "Class level must be >= 1")
    @Max(value = 12, message = "Class level cannot be > 12")
    private Integer classLevel;

    @NotNull(message = "Section is required")
    private String section;   // Example: "A"
}
