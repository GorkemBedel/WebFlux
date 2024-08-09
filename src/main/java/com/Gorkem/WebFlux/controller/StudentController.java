package com.Gorkem.WebFlux.controller;

import com.Gorkem.WebFlux.model.Student;
import com.Gorkem.WebFlux.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
