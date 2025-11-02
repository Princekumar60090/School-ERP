package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "teachers")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Teacher {
    @Id
    private ObjectId id;

    @NotBlank(message = " Teacher name cannot be empty")
    private String name;

    @NotBlank(message = "subject cannot be empty")
    private String subject;

    @NotBlank(message = "phone cannot be empty")
    @Size(min=10,max=10)
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain only digits") // ADDED PATTERN
    private String phone;

    @NotBlank(message = "email cannot be empty")
    @Email(message = "please provide a valid email")
    private String email;

    @Positive(message = "salary must be a positive number")
    private double salary;

    @NotNull(message = "Date of joining cannot be null")
    @PastOrPresent(message = "Date of joining cannot be in the future")
    private LocalDate dateOfJoining;

}
