package com.courseservice.course_service.exceptions;

public class ImmutableFieldUpdateException extends RuntimeException {
    public ImmutableFieldUpdateException(String message) {
        super(message);
    }
}
