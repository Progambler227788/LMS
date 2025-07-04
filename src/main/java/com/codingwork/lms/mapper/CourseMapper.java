package com.codingwork.lms.mapper;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.entity.Course;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class CourseMapper {

    public Course toEntity(CreateCourseRequest dto) {
        if (dto == null) return null;
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory().toLowerCase(Locale.ROOT));
        course.setLessons(dto.getLessons());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        return course;
    }

    public void updateEntity(Course course, UpdateCourseRequest dto) {
        if (dto == null || course == null) return;
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory().toLowerCase(Locale.ROOT));
        if (dto.getLessons() != null) {
            course.setLessons(dto.getLessons());
        }
        course.setUpdatedAt(LocalDateTime.now());
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) return null;
        CourseResponse res = new CourseResponse();
        res.setId(course.getId());
        res.setTitle(course.getTitle());
        res.setDescription(course.getDescription());
        res.setInstructorId(course.getInstructorId());
        res.setCategory(course.getCategory().toLowerCase(Locale.ROOT));
        res.setLessons(course.getLessons());
        return res;
    }
}