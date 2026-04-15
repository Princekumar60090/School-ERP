package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    private ObjectId id;

    @NotBlank(message = "Exam name cannot be empty")
    private String name;

    @NotNull(message = "Exam date is required")
    private LocalDate examDate;

    @NotBlank(message = "Exam type cannot be empty")
    private String examType;

    @NotNull(message = "Class level cannot be null")
    @Min(value = 1, message = "Class level must be >= 1")
    @Max(value = 12, message = "Class level cannot be > 12")
    private Integer classLevel;

    @NotBlank(message = "Section cannot be empty")
    private String section;

    private boolean published = false;
}
