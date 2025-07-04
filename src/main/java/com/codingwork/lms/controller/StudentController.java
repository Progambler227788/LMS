package com.codingwork.lms.controller;

import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
     * Get all available courses
     */
    @Operation(summary = "Get All Courses", description = "Retrieve all available courses for students")
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(studentService.getAllCourses());
    }

    /**
     * Get courses by category using request param
     */
    @Operation(
            summary = "Get Courses by Category",
            description = "Retrieve courses filtered by category for students"
    )
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/courses/filter")
    public ResponseEntity<List<CourseResponse>> getCoursesByCategory(@RequestParam String category) {
        return ResponseEntity.ok(studentService.getCoursesByCategory(category));
    }

}
