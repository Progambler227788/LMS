package com.codingwork.lms.service.impl;


import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import com.codingwork.lms.entity.Course;
import com.codingwork.lms.entity.Enrollment;
import com.codingwork.lms.mapper.EnrollmentMapper;
import com.codingwork.lms.repository.CourseRepository;
import com.codingwork.lms.repository.EnrollmentRepository;
import com.codingwork.lms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;



    @Override
    public Enrollment enroll(String userId, String courseId) {
        // Check if already enrolled
        if (isUserEnrolled(userId, courseId)) {
            throw new IllegalStateException("User already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setProgress(0.0);
        enrollment.setCompleted(false);

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public boolean isUserEnrolled(String userId, String courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId).isPresent();
    }

    @Override
    public List<Enrollment> getUserEnrollments(String userId) {
        return enrollmentRepository.findByUserId(userId);
    }


    @Override
    public List<EnrollmentResponse> getUserEnrollmentResponses(String userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);

        return enrollments.stream()
                .map(enrollment -> {
                    Optional<Course> courseOpt = courseRepository.findById(enrollment.getCourseId());
                    return courseOpt.map(course -> enrollmentMapper.toResponse(enrollment, course)).orElse(null);
                })
                .filter(response -> response != null)
                .collect(Collectors.toList());
    }
}
