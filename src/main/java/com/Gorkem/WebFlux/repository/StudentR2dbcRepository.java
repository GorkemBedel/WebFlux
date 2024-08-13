package com.Gorkem.WebFlux.repository;

import com.Gorkem.WebFlux.model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

public interface StudentR2dbcRepository extends ReactiveCrudRepository<Student, UUID> {

}
