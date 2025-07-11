package com.codingwork.lms.entity.subdocument;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    private String title;
    private List<Lesson> lessons;
}

