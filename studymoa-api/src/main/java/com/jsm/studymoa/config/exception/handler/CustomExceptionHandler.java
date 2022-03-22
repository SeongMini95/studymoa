package com.jsm.studymoa.config.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        // TODO: 2022-03-22 임시 controller advice
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }
}
