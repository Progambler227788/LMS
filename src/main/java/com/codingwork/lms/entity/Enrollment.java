package com.codingwork.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "enrollments")
@CompoundIndex(name = "unique_user_course", def = "{'userId': 1, 'courseId': 1}", unique = true)
public class Enrollment {

    @Id
    private String id;
    private String userId;
    private String courseId;
    private LocalDateTime enrolledAt;
    private double progress;
    private boolean completed;
    private List<String> completedLessonTitles;
}
