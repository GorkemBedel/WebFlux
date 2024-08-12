package com.Gorkem.WebFlux.controller;

import com.Gorkem.WebFlux.dto.StudentDto;
import com.Gorkem.WebFlux.model.Student;
import com.Gorkem.WebFlux.service.StudentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @PostMapping("/addStudent")
    public Mono<Student> addStudent(@RequestBody StudentDto student) {
        return studentService.addStudent(student);
    }
}
