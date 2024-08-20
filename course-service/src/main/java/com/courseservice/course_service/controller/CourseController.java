package com.courseservice.course_service.controller;

import com.courseservice.course_service.config.db.annotations.DataSource;
import com.courseservice.course_service.config.db.enums.DataSourceType;
import com.courseservice.course_service.exceptions.EntityIdNotExistException;
import com.courseservice.course_service.exceptions.EmptyRequestBodyException;
import com.courseservice.course_service.exceptions.ImmutableFieldUpdateException;
import com.courseservice.course_service.exceptions.InvalidCourseDataException;
import com.courseservice.course_service.model.Course;
import com.courseservice.course_service.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing courses in the course service application.
 * This controller provides endpoints for creating, retrieving, updating, and deleting courses,
 * as well as searching for courses by name or mentor's email.
 */
@RestController
@RequestMapping("/course-service/courses")
public class CourseController {

  private final CourseService courseService;

  /**
   * Constructs a new {@link CourseController} with the given {@link CourseService}.
   *
   * @param courseService the service layer that handles course-related operations
   */
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  /**
   * Creates a new course.
   *
   * @param course the course data to create
   * @return a {@link ResponseEntity} containing the created course and an HTTP status of {@code 201 Created}
   * @throws InvalidCourseDataException if the course data is invalid
   * @throws EmptyRequestBodyException  if the request body is empty
   */
  @PostMapping
  public ResponseEntity<Course> createCourse(@RequestBody(required = false) Course course)
          throws InvalidCourseDataException, EmptyRequestBodyException {
    if (course == null) {
      throw new EmptyRequestBodyException("Request body is empty");
    }

    Course createdCourse = courseService.createCourse(course);
    return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
  }

  /**
   * Retrieves a course by its ID.
   *
   * @param id the ID of the course to retrieve
   * @return a {@link ResponseEntity} containing the course and an HTTP status of {@code 200 OK}
   * @throws EntityIdNotExistException if the course ID does not exist
   */
  @GetMapping("/{id}")
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<Course> getCourseById(@PathVariable int id)
          throws EntityIdNotExistException {
    Optional<Course> course = courseService.getCourseById(id);
    return course
            .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseThrow(() -> new EntityIdNotExistException("Course ID does not exist"));
  }

  /**
   * Retrieves all courses.
   *
   * @return a {@link ResponseEntity} containing the list of all courses and an HTTP status of {@code 200 OK}
   */
  @GetMapping
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<List<Course>> getAllCourses() {
    List<Course> courses = courseService.getAllCourses();
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  /**
   * Updates an existing course.
   *
   * @param id            the ID of the course to update
   * @param updatedCourse the updated course data
   * @return a {@link ResponseEntity} containing the updated course and an HTTP status of {@code 200 OK}
   * @throws EntityIdNotExistException     if the course ID does not exist
   * @throws ImmutableFieldUpdateException if an attempt is made to update an immutable field
   */
  @PutMapping("/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse)
          throws EntityIdNotExistException, ImmutableFieldUpdateException {
    Optional<Course> course = courseService.updateCourse(id, updatedCourse);
    return course
            .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseThrow(() -> new EntityIdNotExistException("Course ID does not exist"));
  }

  /**
   * Deletes a course by its ID.
   *
   * @param id the ID of the course to delete
   * @return a {@link ResponseEntity} with an HTTP status of {@code 204 No Content}
   * @throws EntityIdNotExistException if the course ID does not exist
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCourse(@PathVariable int id) throws EntityIdNotExistException {
    courseService.deleteCourse(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Retrieves courses by the mentor's email.
   *
   * @param mentorEmail the email of the mentor whose courses to retrieve
   * @return a {@link ResponseEntity} containing the list of courses taught by the mentor and an HTTP status of {@code 200 OK}
   */
  @GetMapping("/mentor")
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<List<Course>> getCoursesByMentorEmail(@RequestParam String mentorEmail) {
    List<Course> courses = courseService.getCoursesByMentorEmail(mentorEmail);
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  /**
   * Searches for courses by their name.
   *
   * @param name the name of the course to search for
   * @return a {@link ResponseEntity} containing the list of courses matching the name and an HTTP status of {@code 200 OK}
   */
  @GetMapping("/search")
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<List<Course>> getCoursesByName(@RequestParam String name) {
    List<Course> courses = courseService.getCoursesByName(name);
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }
}
