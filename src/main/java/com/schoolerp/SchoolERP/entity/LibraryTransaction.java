package com.schoolerp.SchoolERP.entity;

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

@Document(collection = "library_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryTransaction {

    @Id
    private ObjectId id;

    @NotNull(message = "Book ID cannot be null")
    private ObjectId bookId;

    @NotNull(message = "Person ID cannot be null")
    private ObjectId personId;

    @NotBlank(message = "Person type cannot be empty")
    private String personType;

    @NotNull(message = "Issue date cannot be null")
    private LocalDate issueDate;

    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    private LocalDate returnDate;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    @Min(value = 0, message = "Fine cannot be negative")
    private Double fineAmount;

    private boolean reminderSent;
}
