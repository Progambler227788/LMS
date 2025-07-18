package com.codingwork.lms.dto.response.course;

import com.codingwork.lms.entity.subdocument.Section;
import com.codingwork.lms.entity.subdocument.StructuredDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CourseDetailsResponse {
    private String id;
    private String title;
    private StructuredDescription description;
    private String instructorName;
    private String category;
    private String imageUrl;
    private List<Section> sections;
    private double price;
    private boolean isFree;
    private String language;
    private int durationMinutes;

    private double rating;
    private int ratingCount;

    private boolean isEnrolled;


    private String createdAt;
    private String updatedAt;
}
