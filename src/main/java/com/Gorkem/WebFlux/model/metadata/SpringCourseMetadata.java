package com.Gorkem.WebFlux.model.metadata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

public class SpringCourseMetadata extends CourseMetadata {

    private List<String> prerequisites;
    private List<String> topics;
    private String language;
    private String github;
}
