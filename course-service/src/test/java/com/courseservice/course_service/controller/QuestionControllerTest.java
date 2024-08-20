package com.courseservice.course_service.controller;

import com.courseservice.course_service.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest {

  private MockMvc mockMvc;

  @Mock private QuestionService questionService;

  @InjectMocks private QuestionController questionController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
  }

  @AfterEach
  void tearDown() {}

  @Test
  public void testGetSolution() throws Exception {
    // Arrange
    String question = "What is being taught?";
    int lessonId = 7;
    String expectedAnswer = "Java is a programming language.";

    // Mocking the service layer method
    when(questionService.askQuestion(question, lessonId)).thenReturn(expectedAnswer);

    // Act & Assert
    mockMvc
        .perform(
            get("/course-service/question/{lessonId}", lessonId)
                .param("question", question)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Your answer is: " + expectedAnswer));
  }

  @Test
  public void testGetSolutionWithNoQuestion() throws Exception {
    // Arrange
    int lessonId = 1;

    // Act & Assert
    mockMvc
        .perform(
            get("/course-service/question/{lessonId}", lessonId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetSolutionForNonExistentLesson() throws Exception {
    // Arrange
    String question = "What is Java?";
    int lessonId = 99;

    // Mocking the service layer method to return null or throw an exception
    when(questionService.askQuestion(question, lessonId)).thenReturn(null);

    // Act & Assert
    mockMvc
        .perform(
            get("/course-service/question/{lessonId}", lessonId)
                .param("question", question)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Your answer is: null"));
  }
}
