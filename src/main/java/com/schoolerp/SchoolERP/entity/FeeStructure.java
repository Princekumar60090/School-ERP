package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fee_structures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructure {
    @Id
    private ObjectId id;

    @NotNull(message = "Grade level cannot be null")
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 12, message = "Grade must be no more than 12")
    private Integer gradeLevel;

    @NotBlank(message = "Fee type cannot be empty")
    private String feeType;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    private String description;
}
