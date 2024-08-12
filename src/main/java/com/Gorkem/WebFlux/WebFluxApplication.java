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
public class WebFluxApplication  {


	public static void main(String[] args) {
		SpringApplication.run(WebFluxApplication.class, args);
	}



}
