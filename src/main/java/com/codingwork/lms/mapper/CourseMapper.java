package com.codingwork.lms.mapper;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.entity.User;
import com.codingwork.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final UserRepository userRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Course toEntity(CreateCourseRequest dto) {
        if (dto == null) return null;
        return Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory().toLowerCase(Locale.ROOT))
                .lessons(dto.getLessons())
                .price(dto.getPrice())
                .rating(0.0) // Default rating
                .ratingCount(0) // No ratings initially
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void updateEntity(Course course, UpdateCourseRequest dto) {
        if (dto == null || course == null) return;
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory().toLowerCase(Locale.ROOT));
        course.setPrice(dto.getPrice());
        if (dto.getLessons() != null) {
            course.setLessons(dto.getLessons());
        }
        course.setUpdatedAt(LocalDateTime.now());
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) return null;

        // Fetch instructor name
        String instructorName = userRepository.findById(new ObjectId(course.getInstructorId()))
                .map(User::getUsername)
                .orElse("Unknown Instructor");

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructorId(course.getInstructorId())
                .instructorName(instructorName)
                .category(course.getCategory())
                .lessons(course.getLessons())
                .price(course.getPrice())
                .rating(course.getRating())
                .ratingCount(course.getRatingCount())
                .createdAt(course.getCreatedAt().format(formatter))
                .updatedAt(course.getUpdatedAt().format(formatter))
                .build();
    }
}