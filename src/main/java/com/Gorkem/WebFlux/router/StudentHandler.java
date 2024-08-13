package com.Gorkem.WebFlux.router;

import com.Gorkem.WebFlux.exceptions.StudentNotFoundException;
import com.Gorkem.WebFlux.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class StudentHandler {

    public final StudentService studentService;

    public StudentHandler(StudentService service) {
        this.studentService = service;
    }

    public Mono<ServerResponse> HandleFindAllStudentsWithCourses(ServerRequest serverRequest){


        return studentService.findAllStudentsWithCourses()
                .flatMap(s -> ServerResponse.ok().bodyValue(s))
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(new RuntimeException("No students found"))));

    }



}
