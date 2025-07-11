package com.codingwork.lms.dto.request.course;

import com.codingwork.lms.entity.subdocument.Section;
import com.codingwork.lms.entity.subdocument.StructuredDescription;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateCourseRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String category;

    private StructuredDescription structuredDescription;

    private List<Section> sections;

    private double price;
    private boolean isFree;
    private String language;
    private int durationMinutes;
}
