package com.codingwork.lms.entity;

import com.codingwork.lms.entity.subdocument.Lesson;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
@CompoundIndex(name = "text_search_idx", def = "{'title': 'text', 'description': 'text'}")
@CompoundIndex(name = "category_title_idx", def = "{'category': 1, 'title': 1}")
@CompoundIndex(name = "price_rating_idx", def = "{'price': 1, 'rating': -1}")
public class Course {
    @Id
    private String id;

    private String title;
    private String description;
    private String instructorId;

    @Indexed
    private String category;

    private String imageUrl;
    private List<Lesson> lessons;
    private double price;
    private double rating;
    private int ratingCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}