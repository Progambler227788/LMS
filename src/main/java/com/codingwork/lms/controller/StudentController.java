package com.codingwork.lms.controller;

import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import com.codingwork.lms.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Endpoints for students to view available courses")
public class StudentController {

    private final StudentService studentService;

    /**
     * Get courses by category using request param
     */
    @Operation(
            summary = "Get Courses by Category",
            description = "Retrieve paginated courses filtered by category for students"
    )
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/courses/filter")
    public ResponseEntity<Page<CourseResponse>> getCoursesByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(studentService.getCoursesByCategory(category, page, size));
    }


    @Operation(
            summary = "Get Courses (Paginated & Searchable)",
            description = "Retrieve paginated and searchable courses for students. You can filter by category and search keywords."
    )
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/courses")
    public ResponseEntity<Page<CourseResponse>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(studentService.getCourses(page, size, category, search));
    }


    @Operation(
            summary = "Enroll in a course",
            description = "Allows a student to enroll in a course by course ID"
    )
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/courses/enroll/{courseId}")
    public ResponseEntity<Void> enrollInCourse(@PathVariable String courseId) {
        studentService.enrollInCourse(courseId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get enrolled courses for student",
            description = "Fetches all courses the logged-in student is enrolled in"
    )
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/enrollments")
    public ResponseEntity<List<EnrollmentResponse>> getUserEnrollments() {
        List<EnrollmentResponse> enrollments = studentService.getUserEnrollments();
        return ResponseEntity.ok(enrollments);
    }


    @Operation(
            summary = "Get course by ID",
            description = "Fetch a single course by its ID for students"
    )
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable String courseId) {
        return studentService.getCourse(courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




}
