package com.courseservice.course_service.service;

import com.courseservice.course_service.exceptions.EntityIdNotExistException;
import com.courseservice.course_service.model.Lesson;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Service interface for managing lesson-related operations in the course service.
 * This interface defines the core methods for creating, retrieving, updating, and deleting lessons,
 * as well as searching for lessons based on specific criteria.
 */
public interface LessonService {

  /**
   * Creates a new lesson.
   *
   * @param lesson the lesson entity to be created
   * @param uploadResult a map containing the result of the file upload operation
   * @return the created lesson
   * @throws ExecutionException if the file upload operation fails
   * @throws InterruptedException if the file upload operation is interrupted
   */
  public Lesson createLesson(Lesson lesson, Map<String, String> uploadResult) throws ExecutionException, InterruptedException;

  /**
   * Retrieves a lesson by its ID.
   *
   * @param id the ID of the lesson to retrieve
   * @return an {@link Optional} containing the lesson if found, or empty if not found
   */
  public Optional<Lesson> getLessonById(int id);

  /**
   * Retrieves all lessons.
   *
   * @return a list of all lessons
   */
  public List<Lesson> getAllLessons();

  /**
   * Updates an existing lesson.
   *
   * @param id the ID of the lesson to update
   * @param updatedLesson the updated lesson entity
   * @return an {@link Optional} containing the updated lesson if the update was successful, or empty if not
   */
  public Optional<Lesson> updateLesson(int id, Lesson updatedLesson);

  /**
   * Deletes a lesson by its ID.
   *
   * @param id the ID of the lesson to delete
   * @return {@code true} if the lesson was successfully deleted, {@code false} otherwise
   */
  public boolean deleteLesson(int id);

  /**
   * Retrieves lessons associated with a specific course ID.
   *
   * @param courseId the ID of the course whose lessons to retrieve
   * @return a list of lessons associated with the given course ID
   */
  public List<Lesson> getLessonsByCourseId(int courseId);

  /**
   * Verifies if a given email belongs to a mentor.
   *
   * @param mentorEmail the email of the mentor to verify
   * @return {@code true} if the email belongs to a mentor, {@code false} otherwise
   */
  public boolean verifyMentorEmail(String mentorEmail);
}
