package com.Gorkem.WebFlux.service;

import com.Gorkem.WebFlux.dto.CourseDto;
import com.Gorkem.WebFlux.model.Course;
import com.Gorkem.WebFlux.repository.CourseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Flux<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Mono<CourseDto> findById(UUID uuid){
        return courseRepository.findById(uuid)
                .map(course -> new CourseDto(course.getName(),course.getDescription(),course.getDuration(),course.getTeacher(),course.getCourseMetadata()));
    }
}
