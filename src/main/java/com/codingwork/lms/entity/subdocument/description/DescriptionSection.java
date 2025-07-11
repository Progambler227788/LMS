package com.codingwork.lms.entity.subdocument.description;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionSection {
    private String heading;
    private List<String> bulletPoints;
}
