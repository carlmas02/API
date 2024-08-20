package com.courseservice.course_service.service;

import com.courseservice.course_service.exceptions.EntityIdNotExistException;
import com.courseservice.course_service.exceptions.ImmutableFieldUpdateException;
import com.courseservice.course_service.exceptions.InvalidCourseDataException;
import com.courseservice.course_service.model.Course;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing course-related operations in the course service. This interface
 * defines the core methods for creating, retrieving, updating, and deleting courses, as well as
 * searching for courses based on specific criteria.
 */
public interface CourseService {

  /**
   * Creates a new course.
   *
   * @param course the course entity to be created
   * @return the created course
   * @throws InvalidCourseDataException if the course data is invalid
   */
  public Course createCourse(Course course) throws InvalidCourseDataException;

  /**
   * Retrieves a course by its ID.
   *
   * @param id the ID of the course to retrieve
   * @return an {@link Optional} containing the course if found, or empty if not found
   */
  public Optional<Course> getCourseById(int id);

  /**
   * Retrieves all courses.
   *
   * @return a list of all courses
   */
  public List<Course> getAllCourses();

  /**
   * Updates an existing course.
   *
   * @param id the ID of the course to update
   * @param updatedCourse the updated course entity
   * @return an {@link Optional} containing the updated course if the update was successful, or
   *     empty if not
   * @throws ImmutableFieldUpdateException if an attempt is made to update an immutable field
   */
  public Optional<Course> updateCourse(int id, Course updatedCourse)
      throws ImmutableFieldUpdateException;

  /**
   * Deletes a course by its ID.
   *
   * @param id the ID of the course to delete
   * @return {@code true} if the course was successfully deleted, {@code false} otherwise
   * @throws EntityIdNotExistException if the course ID does not exist
   */
  public boolean deleteCourse(int id) throws EntityIdNotExistException;

  /**
   * Retrieves courses by the mentor's email.
   *
   * @param mentorEmail the email of the mentor whose courses to retrieve
   * @return a list of courses associated with the given mentor email
   */
  public List<Course> getCoursesByMentorEmail(String mentorEmail);

  /**
   * Retrieves courses by their name.
   *
   * @param name the name of the course(s) to search for
   * @return a list of courses matching the given name
   */
  public List<Course> getCoursesByName(String name);

  /**
   * Verifies if a given email belongs to a mentor.
   *
   * @param mentorEmail the email of the mentor to verify
   * @return {@code true} if the email belongs to a mentor, {@code false} otherwise
   */
  public boolean verifyMentorEmail(String mentorEmail);
}
