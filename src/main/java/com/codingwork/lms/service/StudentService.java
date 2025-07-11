package com.codingwork.lms.service;



import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<CourseResponse> getAllCourses();

    Page<CourseResponse>  getCoursesByCategory(String category, int page, int size);

    Page<CourseResponse> getCourses(int page, int size, String category, String search);

    void enrollInCourse(String courseId);

    List<EnrollmentResponse> getUserEnrollments();

    Optional<CourseResponse> getCourse(String courseId);

}

