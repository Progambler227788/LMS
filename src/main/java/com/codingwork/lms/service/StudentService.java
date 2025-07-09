package com.codingwork.lms.service;



import com.codingwork.lms.dto.response.course.CourseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    List<CourseResponse> getAllCourses();

    Page<CourseResponse>  getCoursesByCategory(String category, int page, int size);

    Page<CourseResponse> getCourses(int page, int size, String category, String search);
}

