package com.codingwork.lms.repository;

import com.codingwork.lms.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {


    Optional<Course> findById(String id);

    List<Course>  findByInstructorName(String instructorName);

    List<Course> findByCategory(String category);

    List<Course> findAll();


    // Include only fields needed for CourseCardResponse
    @Query(
            value = "{}",
            fields = "{" +
                    "'id': 1, " +
                    "'title': 1, " +
                    "'instructorName': 1, " +
                    "'category': 1, " +
                    "'imageUrl': 1, " +
                    "'price': 1, " +
                    "'free': 1, " +
                    "'rating': 1, " +
                    "'ratingCount': 1, " +
                    "'totalLessons': 1, " +                // Needed for totalLessons
                    "}"
    )
    Page<Course> findAll(Pageable pageable);


    // Include only fields needed for CourseCardResponse
    @Query(
            value = "{}",
            fields = "{" +
                    "'id': 1, " +
                    "'title': 1, " +
                    "'instructorName': 1, " +
                    "'category': 1, " +
                    "'imageUrl': 1, " +
                    "'price': 1, " +
                    "'free': 1, " +
                    "'rating': 1, " +
                    "'ratingCount': 1, " +
                    "'totalLessons': 1, " +                // Needed for totalLessons
                    "}"
    )
    Page<Course> findByCategory(String category, Pageable pageable);



    // Include only fields needed for CourseCardResponse
    @Query(
            value = "{}",
            fields = "{" +
                    "'id': 1, " +
                    "'title': 1, " +
                    "'instructorName': 1, " +
                    "'category': 1, " +
                    "'imageUrl': 1, " +
                    "'price': 1, " +
                    "'free': 1, " +
                    "'rating': 1, " +
                    "'ratingCount': 1, " +
                    "'totalLessons': 1, " +                // Needed for totalLessons
                    "}"
    )
    Page<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description, Pageable pageable
    );


    // Include only fields needed for CourseCardResponse
    @Query(
            value = "{}",
            fields = "{" +
                    "'id': 1, " +
                    "'title': 1, " +
                    "'instructorName': 1, " +
                    "'category': 1, " +
                    "'imageUrl': 1, " +
                    "'price': 1, " +
                    "'free': 1, " +
                    "'rating': 1, " +
                    "'ratingCount': 1, " +
                    "'totalLessons': 1, " +                // Needed for totalLessons
                    "}"
    )
    Page<Course> findByCategoryAndTitleContainingIgnoreCaseOrCategoryAndDescriptionContainingIgnoreCase(
            String category1, String title,
            String category2, String description,
            Pageable pageable
    );

    Course save(Course course);
    void deleteById(String id);
}
