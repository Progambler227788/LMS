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
import java.util.ArrayList;
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
        enrollment.setCompletedLessonTitles(new ArrayList<>());

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

    @Override
    public void markLessonComplete(String userId, String courseId, String lessonTitle) {
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new IllegalStateException("User not enrolled"));

        if (enrollment.getCompletedLessonTitles() == null) {
            enrollment.setCompletedLessonTitles(new ArrayList<>());
        }

        if (!enrollment.getCompletedLessonTitles().contains(lessonTitle)) {
            // add the lesson if it does not exist in completion
            enrollment.getCompletedLessonTitles().add(lessonTitle);

            // Calculate progress
            Optional<Course> courseOpt = courseRepository.findById(courseId);
            if (courseOpt.isPresent()) {
                Course course = courseOpt.get();
                // get every section lesson and sum them up to get total lessons
                // section 1 -> l1,l2 , section 2 -> l3,l4
                // sum them -> l1,l2,l3,l4, so 4 size
                int totalLessons = course.getSections().stream()
                        .mapToInt(section -> section.getLessons().size()).sum();
                int completed = enrollment.getCompletedLessonTitles().size();
                double progress = ((double) completed / totalLessons) * 100.0;
                enrollment.setProgress(progress);
                // course completed
                if (progress >= 100.0) {
                    enrollment.setCompleted(true);
                }
            }

            // update the enrollment
            enrollmentRepository.save(enrollment);
        }
    }

}
