package com.courseservice.course_service.exceptions;

public class EmptyRequestBodyException extends Exception {

  public EmptyRequestBodyException(String message) {
    super(message);
  }
}
