package com.codingwork.lms.dto.response.course;


import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseCardResponse {
    private String id;
    private String title;
    private String instructorName;
    private String category;
    private String imageUrl;
    private double price;
    private boolean isFree;
    private double rating;
    private int ratingCount;
    private boolean isEnrolled;
    private int totalLessons;
}
