package com.codingwork.lms.dto.request.course;

import com.codingwork.lms.entity.subdocument.Lesson;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateCourseRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String category;


    private List<Lesson> lessons; // Optional at creation
}