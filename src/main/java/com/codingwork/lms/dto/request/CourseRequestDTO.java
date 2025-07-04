package com.codingwork.lms.dto.request;


import com.codingwork.lms.entity.subdocument.Lesson;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * DTO for creating a course.
 */
@Data
public class CourseRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Instructor ID is required")
    private String instructorId;

    private String category;

    private List<Lesson> lessons;
}

