package com.Gorkem.WebFlux;

import com.Gorkem.WebFlux.model.Course;
import com.Gorkem.WebFlux.model.Student;
import com.Gorkem.WebFlux.model.metadata.MathCourseMetadata;
import com.Gorkem.WebFlux.model.metadata.SpringCourseMetadata;
import com.Gorkem.WebFlux.repository.CourseRepository;
import com.Gorkem.WebFlux.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class WebFluxApplication implements CommandLineRunner {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;

	public WebFluxApplication(CourseRepository courseRepository, StudentRepository studentRepository) {
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Course course = Course.builder()
				.id(UUID.randomUUID())
				.name("Webflux")
				.description("Spring Webflux")
				.duration(10)
				.teacher("Gorkem")
				.courseMetadata(
						SpringCourseMetadata.builder()
								.type("spring")
								.language("Java")
								.github("https://github.com/gorkembedel")
								.prerequisites(List.of("Java", "Spring"))
								.build())
				.isUpdated(false)
				.build();

		//courseRepository.save(course).block();

		Student student = Student.builder()
				.id(UUID.randomUUID())
				.name("John")
				.email("j@j.com")
				.dateOfBirth(LocalDate.of(2000,1,1))
				.courses(Set.of(course.getId().toString()))
				.isUpdated(false)
				.build();

		//studentRepository.save(student).block();



		Course course2 = Course.builder()
				.id(UUID.randomUUID())
				.name("Math")
				.description("Math with FolksdevX")
				.duration(100)
				.teacher("FolksDev")
				.courseMetadata(
						MathCourseMetadata.builder()
								.type("math")
								.level("Calculus II")
								.books(List.of("CALCULUS II"))
								.build())
				.isUpdated(false)
				.build();

		courseRepository.save(course2).block();

		Student student2 = Student.builder()
				.id(UUID.randomUUID())
				.name("FSK")
				.email("j@j.com")
				.dateOfBirth(LocalDate.of(2000,1,1))
				.courses(Set.of(course2.getId().toString()))
				.isUpdated(false)
				.build();

		studentRepository.save(student2).block();

	}

}
