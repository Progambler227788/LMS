package com.codingwork.lms.service.impl;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.mapper.CourseMapper;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.repository.UserRepository;
import com.codingwork.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for course-related operations.
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;

    /**
     * Creates a new course for the authenticated instructor.
     * @param dto The course creation request
     * @return The created course response
     */
    public CourseResponse createCourseForAuthenticatedInstructor(CreateCourseRequest dto) {
        String instructorId = getAuthenticatedInstructorId();
        Course course = courseMapper.toEntity(dto);
        course.setInstructorId(instructorId);
        Course saved = courseRepository.save(course);
        return courseMapper.toResponse(saved);
    }

    /**
     * Updates a course owned by the authenticated instructor.
     * @param id Course id
     * @param dto The update course request
     * @return The updated course response
     */
    public CourseResponse updateCourseForAuthenticatedInstructor(String id, UpdateCourseRequest dto) {
        String instructorId = getAuthenticatedInstructorId();
        Course course = courseRepository.findById(id)
                .filter(c -> c.getInstructorId().equals(instructorId))
                .orElseThrow(() -> new IllegalArgumentException("Course not found or not owned by instructor: " + id));
        courseMapper.updateEntity(course, dto);
        Course updated = courseRepository.save(course);
        return courseMapper.toResponse(updated);
    }

    /**
     * Deletes a course owned by the authenticated instructor.
     * @param id Course id
     */
    public void deleteCourseForAuthenticatedInstructor(String id) {
        String instructorId = getAuthenticatedInstructorId();
        courseRepository.findById(id)
                .filter(c -> c.getInstructorId().equals(instructorId))
                .orElseThrow(() -> new IllegalArgumentException("Course not found or not owned by instructor: " + id));
        courseRepository.deleteById(id);
    }

    /**
     * Gets all courses for the authenticated instructor.
     * @return List of course responses
     */
    public List<CourseResponse> getCoursesForAuthenticatedInstructor() {
        String instructorId = getAuthenticatedInstructorId();
        return courseRepository.findByInstructorId(instructorId)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Gets a course by id for the authenticated instructor.
     * @param id Course id
     * @return Optional with course response if found and owned by instructor
     */
    public Optional<CourseResponse> getCourseByIdForAuthenticatedInstructor(String id) {
        String instructorId = getAuthenticatedInstructorId();
        return courseRepository.findById(id)
                .filter(c -> c.getInstructorId().equals(instructorId))
                .map(courseMapper::toResponse);
    }

    /**
     * Utility method to extract the currently authenticated instructor's user ID.
     * @return instructor user id
     */
    private String getAuthenticatedInstructorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated instructor not found"))
                .getId();
    }

    // Legacy methods for admin/global queries (optional, not used in instructor controller):

    @Override
    public Optional<CourseResponse> getCourseById(String id) {
        return courseRepository.findById(id).map(courseMapper::toResponse);
    }

    @Override
    public List<CourseResponse> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructorId(instructorId)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourseById(String id) {
        courseRepository.deleteById(id);
    }


}