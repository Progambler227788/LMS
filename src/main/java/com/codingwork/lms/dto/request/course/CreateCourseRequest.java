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
public class CreateCourseRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String category;

    private StructuredDescription structuredDescription;

    private List<Section> sections; // Instead of flat List<Lesson>

    private double price;
    private boolean free;

    private String language;         // e.g., "English", "Urdu"
    private int durationMinutes;     // Total duration for frontend display
}
