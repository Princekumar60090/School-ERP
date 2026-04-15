package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "staffs")
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @Id
    private ObjectId id;

    @NotBlank(message = "Staff name cannot be empty")
    private String name;

    @NotBlank(message = "Role cannot be empty")
    private String role;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain only digits")
    private String phone;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Indexed(unique = true)
    private String email;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Salary must be zero or positive")
    private Double salary;

    @NotNull(message = "Date of joining cannot be null")
    @PastOrPresent(message = "Date of joining cannot be in the future")
    private LocalDate dateOfJoining;

    private ObjectId userId;

}
