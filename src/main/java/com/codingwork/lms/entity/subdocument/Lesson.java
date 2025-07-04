package com.codingwork.lms.entity.subdocument;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    private String title;
    private String content;      // markdown, video URL, etc.
    private int durationMinutes;
}

