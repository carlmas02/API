package com.courseservice.course_service.controller;

import com.courseservice.course_service.config.db.annotations.DataSource;
import com.courseservice.course_service.config.db.enums.DataSourceType;
import com.courseservice.course_service.model.Lesson;
import com.courseservice.course_service.service.CloudinaryService;
import com.courseservice.course_service.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * REST controller for managing lessons in the course service application. This controller provides
 * endpoints for creating, retrieving, updating, and deleting lessons, as well as retrieving lessons
 * by course ID.
 */
@RestController
@RequestMapping("/course-service/lessons")
public class LessonController {

  private final LessonService lessonService;
  private final CloudinaryService cloudinaryService;

  /**
   * Constructs a new {@link LessonController} with the given {@link LessonService} and {@link
   * CloudinaryService}.
   *
   * @param lessonService the service layer that handles lesson-related operations
   * @param cloudinaryService the service layer that handles file uploads to Cloudinary
   */
  public LessonController(LessonService lessonService, CloudinaryService cloudinaryService) {
    this.lessonService = lessonService;
    this.cloudinaryService = cloudinaryService;
  }

  /**
   * Creates a new lesson with an associated file upload.
   *
   * @param lesson the lesson data as a JSON string
   * @param file the file to be uploaded
   * @return a {@link ResponseEntity} containing the created lesson and an HTTP status of {@code 201
   *     Created}
   * @throws IOException if there is an error processing the file upload
   * @throws ExecutionException if there is an error executing the upload operation
   * @throws InterruptedException if the file upload is interrupted
   */
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Lesson> createLesson(
      @RequestPart("lesson") String lesson, @RequestPart("file") MultipartFile file)
      throws IOException, ExecutionException, InterruptedException {

    ObjectMapper objectMapper = new ObjectMapper();
    Lesson lesson1 = objectMapper.readValue(lesson, Lesson.class);

    // TODO SOLVE THIS BUG WHERE THE MULTIPART FILE IS NOT ABLE TO GET PASSED TO THE SERVICE LAYER
    Map<String, String> uploadResult = cloudinaryService.uploadFile(file);

    Lesson createdLesson = lessonService.createLesson(lesson1, uploadResult);
    return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
  }

  /**
   * Retrieves a lesson by its ID.
   *
   * @param id the ID of the lesson to retrieve
   * @return a {@link ResponseEntity} containing the lesson and an HTTP status of {@code 200 OK}, or
   *     an HTTP status of {@code 404 Not Found} if the lesson does not exist
   */
  @GetMapping("/{id}")
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
    Optional<Lesson> lesson = lessonService.getLessonById(id);
    return lesson
        .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Retrieves all lessons.
   *
   * @return a {@link ResponseEntity} containing the list of all lessons and an HTTP status of
   *     {@code 200 OK}
   */
  @GetMapping
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<List<Lesson>> getAllLessons() {
    List<Lesson> lessons = lessonService.getAllLessons();
    return new ResponseEntity<>(lessons, HttpStatus.OK);
  }

  /**
   * Updates an existing lesson.
   *
   * @param id the ID of the lesson to update
   * @param updatedLesson the updated lesson data
   * @return a {@link ResponseEntity} containing the updated lesson and an HTTP status of {@code 200
   *     OK}, or an HTTP status of {@code 404 Not Found} if the lesson does not exist
   */
  @PutMapping("/{id}")
  public ResponseEntity<Lesson> updateLesson(
      @PathVariable int id, @RequestBody Lesson updatedLesson) {
    Optional<Lesson> lesson = lessonService.updateLesson(id, updatedLesson);
    return lesson
        .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Deletes a lesson by its ID.
   *
   * @param id the ID of the lesson to delete
   * @return a {@link ResponseEntity} with an HTTP status of {@code 204 No Content} if the lesson is
   *     deleted, or an HTTP status of {@code 404 Not Found} if the lesson does not exist
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLesson(@PathVariable int id) {
    boolean isDeleted = lessonService.deleteLesson(id);
    if (isDeleted) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Retrieves lessons by their associated course ID.
   *
   * @param courseId the ID of the course whose lessons to retrieve
   * @return a {@link ResponseEntity} containing the list of lessons and an HTTP status of {@code
   *     200 OK}
   */
  @GetMapping("/course/{courseId}")
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable int courseId) {
    List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);
    return new ResponseEntity<>(lessons, HttpStatus.OK);
  }
}
