package com.codingwork.lms.service.impl;


import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.mapper.CourseMapper;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category.toLowerCase())
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
