package com.courseservice.course_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the course service application. This class handles various custom
 * exceptions and provides appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link EntityIdNotExistException} exceptions.
   *
   * @param e the exception thrown when an entity ID does not exist
   * @return a {@link ResponseEntity} containing the exception message and an HTTP status of {@code
   *     404 Not Found}
   */
  @ExceptionHandler(EntityIdNotExistException.class)
  public ResponseEntity<String> handleEntityIdNotExistException(EntityIdNotExistException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles {@link InvalidCourseDataException} exceptions.
   *
   * @param e the exception thrown when course data is invalid
   * @return a {@link ResponseEntity} containing the exception message and an HTTP status of {@code
   *     404 Not Found}
   */
  @ExceptionHandler(InvalidCourseDataException.class)
  public ResponseEntity<String> handleInvalidCourseDataException(InvalidCourseDataException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles {@link EmptyRequestBodyException} exceptions.
   *
   * @param e the exception thrown when the request body is empty
   * @return a {@link ResponseEntity} containing the exception message and an HTTP status of {@code
   *     400 Bad Request}
   */
  @ExceptionHandler(EmptyRequestBodyException.class)
  public ResponseEntity<String> handleEmptyRequestBodyException(EmptyRequestBodyException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles {@link ImmutableFieldUpdateException} exceptions.
   *
   * @param e the exception thrown when an immutable field is attempted to be updated
   * @return a {@link ResponseEntity} containing the exception message and an HTTP status of {@code
   *     422 Unprocessable Entity}
   */
  @ExceptionHandler(ImmutableFieldUpdateException.class)
  public ResponseEntity<String> handleImmutableFieldUpdateException(
      ImmutableFieldUpdateException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Handles {@link TranscriptNotCreatedException} exceptions.
   *
   * @param e the exception thrown when a transcript is not created
   * @return a {@link ResponseEntity} containing the exception message and an HTTP status of {@code
   *     422 Unprocessable Entity}
   */
  @ExceptionHandler(TranscriptNotCreatedException.class)
  public ResponseEntity<String> handleTranscriptNotCreatedException(
      TranscriptNotCreatedException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
