package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private ObjectId id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @NotBlank(message = "ISBN cannot be empty")
    @Indexed(unique = true)
    private String isbn;

    private String publisher;

    @NotNull(message = "Total quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer totalQuantity;

    @Min(value = 0, message = "Available copies cannot be negative")
    private Integer availableCopies;


}
