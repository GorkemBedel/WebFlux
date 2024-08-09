package com.Gorkem.WebFlux.dto;

import com.Gorkem.WebFlux.model.metadata.CourseMetadata;

public record CourseDto(String name,
                        String description,
                        Integer duration,
                        String teacher,
                        CourseMetadata courseMetadata) {
}
