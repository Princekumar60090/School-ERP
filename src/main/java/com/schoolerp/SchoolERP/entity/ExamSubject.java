package com.schoolerp.SchoolERP.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exam_subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubject {

    @Id
    private ObjectId id;

    @NotNull(message = "Exam ID id required")
    private ObjectId examId;

    @NotNull(message = "Subject ID is required")
    private ObjectId subjectId;

    @NotNull(message = "Teacher ID is required")
    private ObjectId teacherId;

    @NotNull(message = "Total marks is required")
    @Min(value = 1, message = "Total marks must be at least 1")
    private Integer totalMarks;

    @NotNull(message = "Passing marks is required")
    @Min(value = 0, message = "Passing marks cannot be negative")
    private Integer passingMarks;

    private boolean editAllowed = false;

}
