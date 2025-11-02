package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Student {

    @Id
    private ObjectId id;
    @NotBlank(message = "rollNo cannot be empty")
    private String rollNo;
    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "Father's name cannot be empty")
    private String fatherName;

    @NotBlank(message = "Mother's name cannot be empty")
    private String motherName;
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 12, message = "Grade must be no more than 12")
    private int grade;
    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain only digits")
    private String phone;
}
