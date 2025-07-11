package com.codingwork.lms.mapper;

import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.entity.Enrollment;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

@Component
public class EnrollmentMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy");

    public EnrollmentResponse toResponse(Enrollment enrollment, Course course) {
        if (enrollment == null || course == null) return null;

        int totalLessons = course.getSections() != null
                ? course.getSections().stream()
                .mapToInt(section -> section.getLessons() != null ? section.getLessons().size() : 0)
                .sum()
                : 0;

        int completedLessons = (int) Math.round((enrollment.getProgress() / 100.0) * totalLessons);

        return EnrollmentResponse.builder()
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseImageUrl(course.getImageUrl())
                .totalLessons(totalLessons)
                .completedLessons(completedLessons)
                .progress(enrollment.getProgress())
                .completed(enrollment.isCompleted())
                .enrolledAt(enrollment.getEnrolledAt().format(FORMATTER))
                .build();
    }

}

