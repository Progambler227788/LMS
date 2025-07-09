package com.codingwork.lms.repository;

import com.codingwork.lms.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {


    Optional<Course> findById(String id);
    List<Course> findByInstructorId(String instructorId);
    List<Course> findByCategory(String category);
    List<Course> findAll();
    Page<Course> findAll(Pageable pageable);

    Page<Course> findByCategory(String category, Pageable pageable);

    Page<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description, Pageable pageable
    );

    Page<Course> findByCategoryAndTitleContainingIgnoreCaseOrCategoryAndDescriptionContainingIgnoreCase(
            String category1, String title,
            String category2, String description,
            Pageable pageable
    );

    Course save(Course course);
    void deleteById(String id);
}
