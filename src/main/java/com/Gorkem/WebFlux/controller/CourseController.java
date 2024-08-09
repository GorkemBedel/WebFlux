package com.Gorkem.WebFlux.controller;

import com.Gorkem.WebFlux.model.Course;
import com.Gorkem.WebFlux.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/course")
public class CourseController {

    private final CourseService  courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Flux<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}
