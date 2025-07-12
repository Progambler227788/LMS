package com.codingwork.lms.service.impl;


import com.codingwork.lms.dto.response.course.CourseResponse;
import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import com.codingwork.lms.exception.InvalidCredentialsException;
import com.codingwork.lms.mapper.CourseMapper;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.repository.UserRepository;
import com.codingwork.lms.security.jwt.JwtUtil;
import com.codingwork.lms.service.CourseService;
import com.codingwork.lms.service.EnrollmentService;
import com.codingwork.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;



    @Override
    public Page<CourseResponse> getCoursesByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  courseService.getCoursesByCategory(category, pageable, userId());
    }

    @Override
    public Page<CourseResponse> getCourses(int page, int size, String category, String search) {
        return courseService.getCourses(page, size, category, search, userId());
    }

    @Override
    public void enrollInCourse(String courseId) {

        enrollmentService.enroll(userId(), courseId);
    }

    @Override
    public List<EnrollmentResponse> getUserEnrollments() {

        return enrollmentService.getUserEnrollmentResponses(userId());
    }

    @Override
    public Optional<CourseResponse> getCourse(String courseId) {
        return courseService.getCourseById(courseId);
    }

    private String userId() {
        String username = jwtUtil.getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"))
                .getId();
    }

    @Override
    public void markLessonComplete(String courseId, String lessonTitle){
        enrollmentService.markLessonComplete(userId(), courseId, lessonTitle);

    }



}
