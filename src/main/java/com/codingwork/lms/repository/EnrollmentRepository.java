package com.codingwork.lms.repository;


import com.codingwork.lms.entity.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    Optional<Enrollment> findByUserIdAndCourseId(String userId, String courseId);

    List<Enrollment> findByUserId(String userId); // For My Courses

    List<Enrollment> findByCourseId(String courseId);

    boolean existsByUserIdAndCourseId(String userId, String courseId);
}

