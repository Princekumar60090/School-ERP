package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Month;

@Document(collection = "salary_payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPayment {
    @Id
    private ObjectId id;

    @NotNull(message = "Person ID cannot be null")
    private ObjectId personId;

    @NotBlank(message = "Person type cannot be empty")
    private String personType;

    @NotNull(message = "Payment date cannot be null")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    private Double amount;

    @NotNull(message = "Salary month cannot be null")
    private Month salaryMonth;

    @NotNull(message = "Salary year cannot be null")
    @Min(value = 2000, message = "Year must be 2000 or later")
    private Integer salaryYear;

    private String notes;
}
