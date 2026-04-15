package com.schoolerp.SchoolERP.entity;

import com.fasterxml.jackson.annotation.JsonFormat; // 🔧 Added for enum mapping
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex; // 🔧 Added for unique constraint

import java.time.LocalDate;
import java.time.Month;

@Document(collection = "fee_payments")
@CompoundIndex(
        name = "unique_payment_per_month",
        def = "{'studentId': 1, 'feeType': 1, 'paymentMonth': 1, 'paymentYear': 1}",
        unique = true
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeePayment {
    @Id
    private ObjectId id;

    @NotNull(message = "Student ID cannot be null")
    private ObjectId studentId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amountPaid;

    @NotNull(message = "Payment date cannot be null")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @NotBlank(message = "Fee type cannot be empty")
    private String feeType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Month paymentMonth;

    private Integer paymentYear;

    private String notes;
}
