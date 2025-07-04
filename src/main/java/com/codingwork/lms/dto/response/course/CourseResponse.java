package com.codingwork.lms.dto.response.course;

import com.codingwork.lms.entity.subdocument.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for returning course data to client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private String id;
    private String title;
    private String description;
    private String instructorId;
    private String category;
    private List<Lesson> lessons;
}
