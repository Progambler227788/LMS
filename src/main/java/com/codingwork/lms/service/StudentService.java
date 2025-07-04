package com.codingwork.lms.service;



import com.codingwork.lms.dto.response.course.CourseResponse;

import java.util.List;

public interface StudentService {

    List<CourseResponse> getAllCourses();

    List<CourseResponse> getCoursesByCategory(String category);
}

