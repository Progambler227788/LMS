package com.codingwork.lms.controller;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseDetailsResponse;
import com.codingwork.lms.service.impl.CourseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles instructor-specific course operations.
 */
@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final CourseServiceImpl courseService;

    /**
     * Create a new course for the authenticated instructor.
     * Only users with the INSTRUCTOR role can access this endpoint.
     */
    @Operation(summary = "Create Course", description = "Create a new course for the authenticated instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping("/course")
    public ResponseEntity<CourseDetailsResponse> createCourse(@Valid @RequestBody CreateCourseRequest dto) {
        CourseDetailsResponse course = courseService.createCourseForAuthenticatedInstructor(dto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    /**
     * Update an existing course for the authenticated instructor.
     * Only users with the INSTRUCTOR role can access this endpoint.
     * @param id The course ID (path variable)
     * @param dto The update course request body
     */
    @Operation(summary = "Update Course", description = "Update an existing course owned by the authenticated instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/course/{id}")
    public ResponseEntity<CourseDetailsResponse> updateCourse(@PathVariable String id,
                                                              @Valid @RequestBody UpdateCourseRequest dto) {
        CourseDetailsResponse updated = courseService.updateCourseForAuthenticatedInstructor(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a course owned by the authenticated instructor.
     * Only users with the INSTRUCTOR role can access this endpoint.
     * @param id The course ID (path variable)
     */
    @Operation(summary = "Delete Course", description = "Delete a course owned by the authenticated instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/course/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourseForAuthenticatedInstructor(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Get all courses for the authenticated instructor.
     * Only users with the INSTRUCTOR role can access this endpoint.
     */
    @Operation(summary = "Get My Courses", description = "Get all courses created by the authenticated instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping("/courses/me")
    public ResponseEntity<List<CourseDetailsResponse>> getMyCourses() {
        List<CourseDetailsResponse> list = courseService.getCoursesForAuthenticatedInstructor();
        return ResponseEntity.ok(list);
    }

    /**
     * Get a course by id (instructor view).
     * Only users with the INSTRUCTOR role can access this endpoint.
     */
    @Operation(summary = "Get Course by ID", description = "Get a course by ID if owned by the authenticated instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping("/course/{id}")
    public ResponseEntity<CourseDetailsResponse> getCourseById(@PathVariable String id) {
        return courseService.getCourseByIdForAuthenticatedInstructor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}