package com.Gorkem.WebFlux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record StudentDto(
        String name,
        String email,
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate dateOfBirth,
        List<String> courseNames,
        List<CourseDto> courseDtos){
}
