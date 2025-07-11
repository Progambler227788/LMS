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
                .description(dto.getStructuredDescription())
                .category(dto.getCategory().toLowerCase(Locale.ROOT))
                .sections(dto.getSections())
                .price(dto.getPrice())
                .free(dto.isFree())
                .language(dto.getLanguage())
                .durationMinutes(dto.getDurationMinutes())
                .rating(0.0)
                .ratingCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void updateEntity(Course course, UpdateCourseRequest dto) {
        if (dto == null || course == null) return;

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getStructuredDescription());
        course.setCategory(dto.getCategory().toLowerCase(Locale.ROOT));
        course.setPrice(dto.getPrice());
        course.setFree(dto.isFree());
        course.setLanguage(dto.getLanguage());
        course.setDurationMinutes(dto.getDurationMinutes());

        if (dto.getSections() != null) {
            course.setSections(dto.getSections());
        }

        course.setUpdatedAt(LocalDateTime.now());
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) return null;

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
                .imageUrl(course.getImageUrl())
                .sections(course.getSections())
                .price(course.getPrice())
                .isFree(course.isFree())
                .language(course.getLanguage())
                .durationMinutes(course.getDurationMinutes())
                .rating(course.getRating())
                .ratingCount(course.getRatingCount())
                .createdAt(course.getCreatedAt().format(formatter))
                .updatedAt(course.getUpdatedAt().format(formatter))
                .build();
    }
}
