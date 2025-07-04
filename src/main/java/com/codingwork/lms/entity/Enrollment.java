package com.codingwork.lms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "enrollments")
public class Enrollment {

    @Id
    private String id;

    private String userId;
    private String courseId;

    private LocalDateTime enrolledAt;
    private double progress;
    private boolean completed;


}
