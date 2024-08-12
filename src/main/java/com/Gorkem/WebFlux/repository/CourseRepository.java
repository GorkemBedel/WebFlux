package com.Gorkem.WebFlux.repository;

import com.Gorkem.WebFlux.model.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends ReactiveCrudRepository<Course, UUID> {
    Mono<Course> findCourseByName(String name);

    Mono<Course> findCourseById(UUID id);
}
