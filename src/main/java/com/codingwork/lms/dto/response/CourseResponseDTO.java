package com.codingwork.lms.dto.response;

import com.codingwork.lms.entity.subdocument.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO for returning course data to client.
 */
@Data
@AllArgsConstructor
public class CourseResponseDTO {

    private String id;
    private String title;
    private String description;
    private String instructorId;
    private String category;
    private List<Lesson> lessons;
}
