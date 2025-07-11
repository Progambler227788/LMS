package com.codingwork.lms.entity.subdocument;



import com.codingwork.lms.entity.subdocument.description.DescriptionSection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StructuredDescription {
    private List<DescriptionSection> sections;
    private List<String> hashtags;
}

