package com.codingwork.lms.service;

import com.codingwork.lms.dto.request.CourseRequestDTO;
import com.codingwork.lms.dto.response.CourseResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for course-related operations.
 */
public interface CourseService {

    /**
     * Creates a new course.
     */
    CourseResponseDTO createCourse(CourseRequestDTO dto);

    /**
     * Gets course by ID.
     */
    Optional<CourseResponseDTO> getCourseById(String id);

    /**
     * Gets courses by instructor.
     */
    List<CourseResponseDTO> getCoursesByInstructor(String instructorId);

    /**
     * Gets courses by category.
     */
    List<CourseResponseDTO> getCoursesByCategory(String category);

    /**
     * Gets all courses.
     */
    List<CourseResponseDTO> getAllCourses();

    /**
     * Deletes course by ID.
     */
    void deleteCourseById(String id);
}

