package com.codingwork.lms.entity;

import com.codingwork.lms.entity.subdocument.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    private String title;
    private String description;
    private String instructorId;
    private String category; // Enum: PROGRAMMING, DESIGN, BUSINESS, etc.
    private String imageUrl;
    private List<Lesson> lessons;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

