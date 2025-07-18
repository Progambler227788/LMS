package com.codingwork.lms.mapper;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseCardResponse;
import com.codingwork.lms.dto.response.course.CourseDetailsResponse;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.entity.User;
import com.codingwork.lms.entity.subdocument.Section;
import com.codingwork.lms.entity.subdocument.StructuredDescription;
import com.codingwork.lms.entity.subdocument.description.DescriptionSection;
import com.codingwork.lms.repository.EnrollmentRepository;
import com.codingwork.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Course toEntity(CreateCourseRequest dto) {
        if (dto == null) return null;

        return Course.builder()
                .title(dto.getTitle())
                .imageUrl(dto.getImageUrl())
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
        course.setImageUrl(dto.getImageUrl());
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

    public CourseDetailsResponse toResponse(Course course) {
        if (course == null) return null;

        String instructorName = userRepository.findById(new ObjectId(course.getInstructorId()))
                .map(User::getUsername)
                .orElse("Unknown Instructor");


        return CourseDetailsResponse.builder()
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

    public CourseDetailsResponse toStudentCourseResponse(Course course, String userId) {
        if (course == null) return null;

        String instructorName = userRepository.findById(new ObjectId(course.getInstructorId()))
                .map(User::getUsername)
                .orElse("Unknown Instructor");

        boolean isEnrolled = enrollmentRepository.existsByUserIdAndCourseId(userId, course.getId());

        return CourseDetailsResponse.builder()
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
                .isEnrolled(isEnrolled)
                .build();
    }

    public CourseCardResponse toCardResponse(Course course, String userId) {
        if (course == null) return null;

        String instructorName = userRepository.findById(new ObjectId(course.getInstructorId()))
                .map(User::getUsername)
                .orElse("Unknown Instructor");

        boolean isEnrolled = enrollmentRepository.existsByUserIdAndCourseId(userId, course.getId());

        return CourseCardResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .instructorName(instructorName)
                .category(course.getCategory())
                .imageUrl(course.getImageUrl())
                .price(course.getPrice())
                .isFree(course.isFree())
                .rating(course.getRating())
                .ratingCount(course.getRatingCount())
                .isEnrolled(isEnrolled)
                .totalLessons(calculateTotalLessons(course.getSections()))
                .firstDescription(extractFirstDescription(course.getDescription()))
                .build();
    }


    private int calculateTotalLessons(List<Section> sections) {
        if (sections == null) return 0;
        return sections.stream()
                .mapToInt(section -> section.getLessons() != null ? section.getLessons().size() : 0)
                .sum();
    }

    private String extractFirstDescription(StructuredDescription description) {
        if (description == null || description.getSections() == null || description.getSections().isEmpty()) {
            return "";
        }
        List<DescriptionSection> sections = description.getSections();
        if (!sections.get(0).getBulletPoints().isEmpty()) {
            return sections.get(0).getBulletPoints().get(0); // First bullet of first section
        }
        return "";
    }
}
