package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "school_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {

    @Id
    private ObjectId id;

    @NotNull(message = "grade level cannot be null")
    @Min(value=1, message ="grade must be at least 1" )
    @Max(value=12, message = "grade must be no more than 12")
    private Integer gradeLevel;

    @NotBlank(message = "section cannot be empty")
    private String section;

    private List<ObjectId> subjectIds;

    private List<ObjectId> studentIds;

    private ObjectId classTeacherId;


}
