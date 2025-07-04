package com.codingwork.lms.service.impl;



import com.codingwork.lms.dto.request.CourseRequestDTO;
import com.codingwork.lms.dto.response.CourseResponseDTO;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Course Service implementation for handling course logic.
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setInstructorId(dto.getInstructorId());
        course.setCategory(dto.getCategory());
        course.setLessons(dto.getLessons());

        Course saved = courseRepository.save(course);
        return toDTO(saved);
    }

    @Override
    public Optional<CourseResponseDTO> getCourseById(String id) {
        return courseRepository.findById(id).map(this::toDTO);
    }

    @Override
    public List<CourseResponseDTO> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructorId(instructorId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponseDTO> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourseById(String id) {
        courseRepository.deleteById(id);
    }

    private CourseResponseDTO toDTO(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorId(),
                course.getCategory(),
                course.getLessons()
        );
    }
}
