package com.courseservice.course_service.service;

/**
 * Service interface for handling question-related operations in the course service. This interface
 * defines the core method for asking a question and retrieving an answer.
 */
public interface QuestionService {

  /**
   * Asks a question about a specific lesson and retrieves the answer.
   *
   * @param question the question to be asked
   * @param lessonId the ID of the lesson related to the question
   * @return the answer to the question
   */
  public String askQuestion(String question, int lessonId);
}
