package com.codingwork.lms.service.impl;


import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.mapper.CourseMapper;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.service.CourseService;
import com.codingwork.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Override
    public Page<CourseResponse> getCoursesByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  courseService.getCoursesByCategory(category, pageable);
    }

    @Override
    public Page<CourseResponse> getCourses(int page, int size, String category, String search) {
        return courseService.getCourses(page, size, category, search);
    }
}
