package com.Gorkem.WebFlux.dto;

import java.util.List;

public record StudentDto(String name, String email, List<CourseDto> courseDtos){
}
