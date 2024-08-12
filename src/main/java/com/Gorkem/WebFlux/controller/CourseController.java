package com.Gorkem.WebFlux.controller;

import com.Gorkem.WebFlux.dto.CourseDto;
import com.Gorkem.WebFlux.model.Course;
import com.Gorkem.WebFlux.service.CourseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/v1/courseController")
public class CourseController {

    private final CourseService  courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/AllCourses")
    public Flux<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/findById/{UUID}")
    public Mono<CourseDto> getCourseById(@PathVariable UUID UUID) {
        return courseService.findById(UUID);
    }

    @PostMapping("/AddNewCourse")
    public Mono<Course> addCourse(@RequestBody CourseDto course) {
        return courseService.addCourse(course);
    }
}
