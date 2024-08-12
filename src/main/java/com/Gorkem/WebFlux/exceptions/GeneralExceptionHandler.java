package com.Gorkem.WebFlux.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> CourseNameNotUniqueException(DuplicateKeyException exception){
        return new ResponseEntity<>("Course name not unique", HttpStatus.BAD_REQUEST);
    }

}
