package com.Gorkem.WebFlux.service;

import com.Gorkem.WebFlux.dto.CourseDto;
import com.Gorkem.WebFlux.dto.StudentDto;
import com.Gorkem.WebFlux.dto.StudentListDto;
import com.Gorkem.WebFlux.model.Student;
import com.Gorkem.WebFlux.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public Flux<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Mono<StudentListDto> findAllStudentsWithCourses() {
        return studentRepository.findAll().flatMap(
                student -> {

                    List<Mono<CourseDto>> courseDtoList =
                            student.getCourses()
                            .stream()
                            .map(courseId -> courseService.findById(UUID.fromString(String.valueOf(courseId))))
                            .collect(Collectors.toList());

                    Flux<CourseDto> courseDtoFlux = Flux.merge(courseDtoList);
// *******************************************************************************************************************
                    Mono<List<String>> courseNamesList =
                            Flux.fromIterable(student.getCourses()) //Gets students courses UUID's and find the course names from UUID's
                            .flatMap(courseService::findNameByUUID)
                            .collect(Collectors.toList());

                    Flux<List<String>> courseNamesFlux = courseNamesList.flatMapMany(Flux::just);
// *******************************************************************************************************************

                    return Flux.combineLatest(
                            courseDtoFlux.collectList(),
                            courseNamesFlux,
                                    (dtos, names) -> {
                                return new StudentDto(student.getName(),student.getEmail(),student.getDateOfBirth(), names,dtos);
                            });

                })
                .collectList()
                .map(StudentListDto::new);

    }

    public Mono<Student> addStudent(StudentDto studentDto) {

        System.out.println("Received StudentDto: " + studentDto);


        return Flux.fromIterable(studentDto.courseNames()) // courseNames liste elemanları üzerinde Flux oluşturur
                .flatMap(courseName -> courseService.findUUIDByName(courseName) // Her courseName için UUID döner
                        .switchIfEmpty(Mono.empty())) // Eğer UUID bulunamazsa boş geç
                .collect(Collectors.toSet()) // UUID'leri bir Set'e toplar
                .map(courseIds -> {
                    System.out.println("Course IDs: " + courseIds);
                    return Student.builder()
                            .id(UUID.randomUUID())
                            .name(studentDto.name())
                            .email(studentDto.email())
                            .courses(courseIds) // UUID'leri kullanır
                            .dateOfBirth(studentDto.dateOfBirth())
                            .isUpdated(false)
                            .build();
                })
                .flatMap(studentRepository::save)
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
        }

    }
