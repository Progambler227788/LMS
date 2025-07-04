package com.codingwork.lms.repository;

import com.codingwork.lms.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {


    Optional<Course> findById(String id);
    List<Course> findByInstructorId(String instructorId);
    List<Course> findByCategory(String category);
    List<Course> findAll();
    Course save(Course course);
    void deleteById(String id);
}
