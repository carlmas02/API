package com.project.userservice.exceptions;

public class InvalidInstructorDataException extends RuntimeException {
    public InvalidInstructorDataException(String message) {
        super(message);
    }
}