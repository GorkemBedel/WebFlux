package com.Gorkem.WebFlux.model.metadata;

import com.Gorkem.WebFlux.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = SpringCourseMetadata.class, name = CourseConstants.SPRING_COURSE_TYPE),
        @JsonSubTypes.Type(value = MathCourseMetadata.class, name = CourseConstants.MATH_COURSE_TYPE)
    }
)

@SuperBuilder
public class CourseMetadata {

    @JsonIgnore
    private String type;
}