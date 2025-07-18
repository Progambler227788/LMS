package com.codingwork.lms.service.impl;

import com.codingwork.lms.dto.request.course.CreateCourseRequest;
import com.codingwork.lms.dto.request.course.UpdateCourseRequest;
import com.codingwork.lms.dto.response.course.CourseCardResponse;
import com.codingwork.lms.dto.response.course.CourseDetailsResponse;
import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.entity.Enrollment;
import com.codingwork.lms.mapper.CourseMapper;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.repository.UserRepository;
import com.codingwork.lms.service.CourseService;
import com.codingwork.lms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Service implementation for course-related operations.
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private final EnrollmentService enrollmentService;

    /**
     * Creates a new course for the authenticated instructor.
     * @param dto The course creation request
     * @return The created course response
     */
    public CourseDetailsResponse createCourseForAuthenticatedInstructor(CreateCourseRequest dto) {
        String instructorName = getInstructorNameAuthenticated();
        Course course = courseMapper.toEntity(dto);
        course.setInstructorName(instructorName);
        course.setTotalLessons(dto.getSections()
                .stream()
                .mapToInt(section -> section.getLessons().size())
                .sum());
        Course saved = courseRepository.save(course);
        return courseMapper.toResponse(saved);
    }

    /**
     * Updates a course owned by the authenticated instructor.
     * @param id Course id
     * @param dto The update course request
     * @return The updated course response
     */
    public CourseDetailsResponse updateCourseForAuthenticatedInstructor(String id, UpdateCourseRequest dto) {
        String instructorName = getInstructorNameAuthenticated();
        Course course = courseRepository.findById(id)
                .filter(c -> c.getInstructorName().equals(instructorName ))
                .orElseThrow(() -> new IllegalArgumentException("Course not found or not owned by instructor: " + id));
        courseMapper.updateEntity(course, dto);
        Course updated = courseRepository.save(course);
        return courseMapper.toResponse(updated);
    }

    /**
     * Deletes a course owned by the authenticated instructor.
     * @param id Course id
     */
    public void deleteCourseForAuthenticatedInstructor(String id) {
        String instructorName = getInstructorNameAuthenticated();
        courseRepository.findById(id)
                .filter(c -> c.getInstructorName().equals(instructorName ))
                .orElseThrow(() -> new IllegalArgumentException("Course not found or not owned by instructor: " + id));
        courseRepository.deleteById(id);
    }

    /**
     * Gets all courses for the authenticated instructor.
     * @return List of course responses
     */
    public List<CourseDetailsResponse> getCoursesForAuthenticatedInstructor() {
        String instructorName = getInstructorNameAuthenticated();
        return courseRepository.findByInstructorName(instructorName)
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    /**
     * Gets a course by id for the authenticated instructor.
     * @param id Course id
     * @return Optional with course response if found and owned by instructor
     */
    public Optional<CourseDetailsResponse> getCourseByIdForAuthenticatedInstructor(String id) {
        String instructorName = getInstructorNameAuthenticated();
        return courseRepository.findById(id)
                .filter(c -> c.getInstructorName().equals(instructorName ))
                .map(courseMapper::toResponse);
    }

    /**
     * Utility method to extract the currently authenticated instructor's user ID.
     * @return instructor user id
     */
    private String getInstructorNameAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        }
        else {
            username = principal.toString();
        }
        return username;
    }


    @Override
    public Optional<CourseDetailsResponse> getCourseById(String id) {
        return courseRepository.findById(id).map(courseMapper::toResponse);
    }

    @Override
    public List<CourseDetailsResponse> getCoursesByInstructor(String instructorName) {
        return courseRepository.findByInstructorName(instructorName)
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }



    @Override
    public List<CourseDetailsResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCourseById(String id) {
        courseRepository.deleteById(id);
    }

    private boolean isValid(String val) {
        return val != null && !val.trim().isEmpty();
    }


    private Set<String> fetchEnrollments(String userId)
    {
        List<EnrollmentResponse> enrollments = enrollmentService.getUserEnrollmentResponses(userId);
        return  enrollments.stream()
                .map(EnrollmentResponse::getCourseId)
                .collect(Collectors.toSet());
    }


    @Override
    public Page<CourseCardResponse> getCoursesByCategory(String category, Pageable pageable, String userId) {
        Set<String> enrolledCourseIds =fetchEnrollments(userId);
        return courseRepository.findByCategory(category.toLowerCase(), pageable)
                .map( course -> courseMapper.toCardResponse(course, enrolledCourseIds));
    }


    @Override
    public Page<CourseCardResponse> getCourses(int page, int size, String category, String search, String userId) {
        Pageable pageable = PageRequest.of(page, size);

        boolean hasCategory = isValid(category);
        boolean hasSearch = isValid(search);
        Set<String> enrolledCourseIds =fetchEnrollments(userId);


        if (hasCategory && hasSearch) {
            return courseRepository
                    .findByCategoryAndTitleContainingIgnoreCaseOrCategoryAndDescriptionContainingIgnoreCase(
                            category, search, category, search, pageable
                    )
                    .map(course -> courseMapper.toCardResponse(course, enrolledCourseIds));
        }

        if (hasCategory) {
            return courseRepository.findByCategory(category, pageable)
                    .map(course -> courseMapper.toCardResponse(course, enrolledCourseIds));
        }

        if (hasSearch) {
            return courseRepository
                    .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                            search, search, pageable
                    )
                    .map(course -> courseMapper.toCardResponse(course, enrolledCourseIds));
        }

        return courseRepository.findAll(pageable).map(course -> courseMapper.toCardResponse(course, enrolledCourseIds));
    }



}