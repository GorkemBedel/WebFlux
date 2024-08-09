package com.Gorkem.WebFlux.repository;

import com.Gorkem.WebFlux.model.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CourseRepository extends ReactiveCrudRepository<Course, UUID> {
}
