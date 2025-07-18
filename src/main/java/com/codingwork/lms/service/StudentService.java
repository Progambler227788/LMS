package com.codingwork.lms.service;



import com.codingwork.lms.dto.response.course.CourseCardResponse;
import com.codingwork.lms.dto.response.course.CourseDetailsResponse;
import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {


    Page<CourseCardResponse>  getCoursesByCategory(String category, int page, int size);

    Page<CourseCardResponse> getCourses(int page, int size, String category, String search);

    void enrollInCourse(String courseId);

    List<EnrollmentResponse> getUserEnrollments();

    Optional<CourseDetailsResponse> getCourse(String courseId);

    void markLessonComplete(String courseId, String lessonTitle);

}

