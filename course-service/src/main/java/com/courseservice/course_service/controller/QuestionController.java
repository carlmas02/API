package com.courseservice.course_service.controller;

import com.courseservice.course_service.config.db.annotations.DataSource;
import com.courseservice.course_service.config.db.enums.DataSourceType;
import com.courseservice.course_service.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling questions related to a specific lesson in the course service.
 * This controller provides an endpoint to get the solution to a question based on a lesson ID.
 */
@RestController
@RequestMapping("course-service/question")
public class QuestionController {

  private final QuestionService questionService;

  /**
   * Constructs a new {@link QuestionController} with the given {@link QuestionService}.
   *
   * @param questionService the service layer that handles question-related operations
   */
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  /**
   * Retrieves the solution to a specific question based on the lesson ID.
   *
   * @param question the question text that needs a solution
   * @param lessonId the ID of the lesson associated with the question
   * @return a {@link ResponseEntity} containing the answer to the question prefixed with "Your answer is: "
   */
  @RequestMapping("{lessonId}")
  @DataSource(DataSourceType.SLAVE)
  public ResponseEntity<String> getSolution(
          @RequestParam String question, @PathVariable int lessonId) {

    String output = questionService.askQuestion(question, lessonId);
    return ResponseEntity.ok("Your answer is: " + output);
  }
}
