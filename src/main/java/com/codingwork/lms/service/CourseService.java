package com.codingwork.lms.service;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for course-related operations.
 */
public interface CourseService {

    /**
     * Creates a new course for the authenticated instructor.
     * @param dto The course creation request
     * @return The created course response
     */
    CourseResponse createCourseForAuthenticatedInstructor(CreateCourseRequest dto);

    /**
     * Gets course by ID for the authenticated instructor.
     * @param id The course ID
     * @return Optional course response if found and owned by instructor
     */
    Optional<CourseResponse> getCourseByIdForAuthenticatedInstructor(String id);

    /**
     * Gets all courses for the authenticated instructor.
     * @return List of course responses
     */
    List<CourseResponse> getCoursesForAuthenticatedInstructor();

    /**
     * Updates an existing course for the authenticated instructor.
     * @param id The course ID
     * @param dto The update course request
     * @return The updated course response
     */
    CourseResponse updateCourseForAuthenticatedInstructor(String id, UpdateCourseRequest dto);

    /**
     * Deletes a course owned by the authenticated instructor.
     * @param id The course ID
     */
    void deleteCourseForAuthenticatedInstructor(String id);

    // Legacy/global methods (for admin/global access)

    /**
     * Gets course by ID (legacy/global).
     * @param id The course ID
     * @return Optional course response
     */
    Optional<CourseResponse> getCourseById(String id);

    /**
     * Gets courses by instructor (legacy/global).
     * @param instructorId The instructor's user ID
     * @return List of course responses
     */
    List<CourseResponse> getCoursesByInstructor(String instructorId);

    /**
     * Gets courses by category.
     * @param category The course category
     * @return List of course responses
     */
    List<CourseResponse> getCoursesByCategory(String category);

    /**
     * Gets all courses (legacy/global).
     * @return List of course responses
     */
    List<CourseResponse> getAllCourses();

    /**
     * Deletes course by ID (legacy/global).
     * @param id The course ID
     */
    void deleteCourseById(String id);
}