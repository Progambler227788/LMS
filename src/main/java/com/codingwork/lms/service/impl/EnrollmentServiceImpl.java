package com.codingwork.lms.service.impl;


import com.codingwork.lms.entity.Enrollment;
import com.codingwork.lms.repository.EnrollmentRepository;
import com.codingwork.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

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
}
