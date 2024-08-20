package com.example.Testing_JWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* Thinking of including more exceptions after a thorough research and learning */
@ControllerAdvice
public class UserException {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException u){
        return new ResponseEntity<>("User Already Exists" , HttpStatus.FOUND);
    }
}
