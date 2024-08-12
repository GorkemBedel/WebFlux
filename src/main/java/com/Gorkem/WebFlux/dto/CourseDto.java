package com.Gorkem.WebFlux.dto;

import com.Gorkem.WebFlux.model.metadata.CourseMetadata;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseDto(String name,
                        String description,
                        Integer duration,
                        String teacher,
                        CourseMetadata courseMetadata) {
}
