package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    private ObjectId id;

    @NotBlank(message = "subject cannot be empty")
    private String subjectName;

    @Indexed(unique=true)
    @NotBlank(message = "subject code cannot be empty")
    private String subjectCode;
}
