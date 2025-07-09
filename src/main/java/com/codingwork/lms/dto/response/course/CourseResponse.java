package com.codingwork.lms.dto.response.course;

import com.codingwork.lms.entity.subdocument.Lesson;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseResponse {
    private String id;
    private String title;
    private String description;
    private String instructorId;
    private String instructorName;
    private String category;
    private String imageUrl;
    private List<Lesson> lessons;
    private double price;
    private double rating;
    private int ratingCount;
    private String createdAt;
    private String updatedAt;
}