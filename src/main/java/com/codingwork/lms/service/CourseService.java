package com.codingwork.lms.service;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseCardResponse;
import com.codingwork.lms.dto.response.course.CourseDetailsResponse;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service interface for course-related operations.
 */
public interface CourseService {

    /**
     * Creates a new course for the authenticated instructor.
     * @param dto The course creation request
     * @return The created course response
     */
    CourseDetailsResponse createCourseForAuthenticatedInstructor(CreateCourseRequest dto);

    /**
     * Gets course by ID for the authenticated instructor.
     * @param id The course ID
     * @return Optional course response if found and owned by instructor
     */
    Optional<CourseDetailsResponse> getCourseByIdForAuthenticatedInstructor(String id);

    /**
     * Gets all courses for the authenticated instructor.
     * @return List of course responses
     */
    List<CourseDetailsResponse> getCoursesForAuthenticatedInstructor();

    /**
     * Updates an existing course for the authenticated instructor.
     * @param id The course ID
     * @param dto The update course request
     * @return The updated course response
     */
    CourseDetailsResponse updateCourseForAuthenticatedInstructor(String id, UpdateCourseRequest dto);

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
    Optional<CourseDetailsResponse> getCourseById(String id);

    /**
     * Gets courses by instructor (legacy/global).
     * @param instructorId The instructor's user ID
     * @return List of course responses
     */
    List<CourseDetailsResponse> getCoursesByInstructor(String instructorId);

    /**
     * Gets courses by category.
     * @param category The course category
     * @return List of course responses
     */
    Page<CourseCardResponse>  getCoursesByCategory(String category, Pageable pageable, String userId);

    /**
     * Gets all courses (legacy/global).
     * @return List of course responses
     */
    List<CourseDetailsResponse> getAllCourses();

    /**
     * Deletes course by ID (legacy/global).
     * @param id The course ID
     */
    void deleteCourseById(String id);

    Page<CourseCardResponse> getCourses(int page, int size, String category, String search, String userId);
}