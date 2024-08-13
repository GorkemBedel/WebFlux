package com.Gorkem.WebFlux.service;

import com.Gorkem.WebFlux.dto.CourseDto;
import com.Gorkem.WebFlux.dto.StudentDto;
import com.Gorkem.WebFlux.dto.StudentListDto;
import com.Gorkem.WebFlux.exceptions.StudentNotFoundException;
import com.Gorkem.WebFlux.model.Student;
import com.Gorkem.WebFlux.repository.StudentR2dbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentR2dbcRepository studentRepository;
    private final CourseService courseService;

    public StudentService(StudentR2dbcRepository studentRepository, CourseService courseService) {
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
//                  ****************************************************************************************************
                    Mono<List<String>> courseNamesList =
                            Flux.fromIterable(student.getCourses()) //Gets students courses UUID's and find the course names from UUID's
                            .flatMap(courseService::findNameByUUID)
                            .collect(Collectors.toList());

                    Flux<List<String>> courseNamesFlux = courseNamesList.flatMapMany(Flux::just);
//                  ****************************************************************************************************

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

    public Mono<Student> updateStudent(UUID id, StudentDto student) {

        return studentRepository.findById(id)
                .map(student1 -> {
                    if(student.name() != null){
                        student1.setName(student.name());
                    }
                    if(student.email() != null){
                        student1.setEmail(student.email());
                    }
                    if(student.dateOfBirth() != null){
                        student1.setDateOfBirth(student.dateOfBirth());
                    }
                    student1.setIsUpdated(true);
                    if(student.courseNames() != null){
                        List<Mono<UUID>> list = student.courseNames().stream().map(courseService::findUUIDByName).
                                collect(Collectors.toList());
                        return Flux.fromIterable(list)
                                .flatMap(mono -> mono)
                                .collectList()
                                .flatMap(uuids -> {
                                    student1.setCourses(convertToSet(uuids));
                                    return Mono.just(student1);
                                });

                    }else{
                        return Mono.just(student1);
                    }


                })
                .flatMap(a -> a)
                .flatMap(studentRepository::save)
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }

    public Mono<String> deleteStudent(UUID id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new StudentNotFoundException("There is no student with ID: " + id)))
                .flatMap(existingStudent -> studentRepository.deleteById(id)
                        .thenReturn("Deleted student with ID: " + id) // Silme işlemi tamamlandığında mesaj döndürülür
                );
    }




    public Mono<List<String>> deleteStudentsByIds(List<UUID> studentIds){

        return Flux.fromIterable(studentIds)
                .flatMap(studentRepository::findById)
                .switchIfEmpty(Mono.error(new StudentNotFoundException("No students found with id :  " + studentIds))) // Eğer dönen student fluxı tamamen boşsa yani verilen UUID listesindeki tüm UUID ler geçersiz ise
                .flatMap(student -> studentRepository.deleteById(student.getId()) //Bir tane bile geçerli UUID varsa silinen öğrencilerin UUID leri return ediliyor.
                        .thenReturn("Deleted student with ID: " + student.getId()) // Silme işlemi tamamlandığında mesaj döndürülür
                ).collect(Collectors.toList());
    }

    public static Set<UUID> convertToSet(List<UUID> uuidList) {
        return new HashSet<>(uuidList);
    }




}
