package com.codingwork.lms.dto.response.enrollment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private String courseId;
    private String courseTitle;
    private String courseImageUrl;
    private int totalLessons;
    private int completedLessons;
    private double progress;
    private boolean completed;
    private String enrolledAt; // formatted for frontend (e.g., "Jul 9, 2025")
}
