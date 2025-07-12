package com.codingwork.lms.service;

import com.codingwork.lms.dto.response.enrollment.EnrollmentResponse;
import com.codingwork.lms.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    Enrollment enroll(String userId, String courseId);
    boolean isUserEnrolled(String userId, String courseId);
    List<Enrollment> getUserEnrollments(String userId);
    List<EnrollmentResponse> getUserEnrollmentResponses(String userId);
    void markLessonComplete(String userId, String courseId, String lessonTitle);

}
