package com.courseservice.course_service.controller;

import com.courseservice.course_service.model.Lesson;
import com.courseservice.course_service.service.CloudinaryService;
import com.courseservice.course_service.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LessonControllerTest {

  private MockMvc mockMvc;

  @Mock private LessonService lessonService;

  @Mock private CloudinaryService cloudinaryService;

  @InjectMocks private LessonController lessonController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();
  }
//
//  @Test
//  public void testCreateLesson() throws Exception {
//    // Arrange
//    Lesson lesson = new Lesson();
//    lesson.setId(1);
//    lesson.setName("Lesson 1");
//
//    String lessonJson = new ObjectMapper().writeValueAsString(lesson);
//    MockMultipartFile file =
//        new MockMultipartFile(
//            "file", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "video content".getBytes());
//
//    when(cloudinaryService.uploadFile(any()))
//        .thenReturn(Collections.singletonMap("secure_url", "http://cloudinary.com/video.mp4"));
//    when(lessonService.createLesson(any(Lesson.class), anyMap())).thenReturn(lesson);
//
//    // Act & Assert
//    mockMvc
//        .perform(
//            multipart("/course-service/lessons")
//                .file(file)
//                .param("lesson", lessonJson)
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
//        .andExpect(status().isCreated())
//        .andExpect(jsonPath("$.id").value(1))
//        .andExpect(jsonPath("$.name").value("Lesson 1"));
//  }

  @Test
  public void testGetLessonById() throws Exception {
    // Arrange
    Lesson lesson = new Lesson();
    lesson.setId(1);
    lesson.setName("Lesson 1");

    when(lessonService.getLessonById(1)).thenReturn(Optional.of(lesson));

    // Act & Assert
    mockMvc
        .perform(get("/course-service/lessons/{id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Lesson 1"));
  }

  @Test
  public void testGetLessonById_NotFound() throws Exception {
    // Arrange
    when(lessonService.getLessonById(1)).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc
        .perform(get("/course-service/lessons/{id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetAllLessons() throws Exception {
    // Arrange
    Lesson lesson = new Lesson();
    lesson.setId(1);
    lesson.setName("Lesson 1");

    when(lessonService.getAllLessons()).thenReturn(Collections.singletonList(lesson));

    // Act & Assert
    mockMvc
        .perform(get("/course-service/lessons").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Lesson 1"));
  }

  @Test
  public void testUpdateLesson() throws Exception {
    // Arrange
    Lesson updatedLesson = new Lesson();
    updatedLesson.setId(1);
    updatedLesson.setName("Updated Lesson");

    when(lessonService.updateLesson(eq(1), any(Lesson.class)))
        .thenReturn(Optional.of(updatedLesson));

    // Act & Assert
    mockMvc
        .perform(
            put("/course-service/lessons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedLesson)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Updated Lesson"));
  }

  @Test
  public void testUpdateLesson_NotFound() throws Exception {
    // Arrange
    Lesson updatedLesson = new Lesson();
    updatedLesson.setId(1);
    updatedLesson.setName("Updated Lesson");

    when(lessonService.updateLesson(eq(1), any(Lesson.class))).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc
        .perform(
            put("/course-service/lessons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedLesson)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteLesson() throws Exception {
    // Arrange
    when(lessonService.deleteLesson(1)).thenReturn(true);

    // Act & Assert
    mockMvc
        .perform(delete("/course-service/lessons/{id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void testDeleteLesson_NotFound() throws Exception {
    // Arrange
    when(lessonService.deleteLesson(1)).thenReturn(false);

    // Act & Assert
    mockMvc
        .perform(delete("/course-service/lessons/{id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetLessonsByCourseId() throws Exception {
    // Arrange
    Lesson lesson = new Lesson();
    lesson.setId(1);
    lesson.setName("Lesson 1");

    when(lessonService.getLessonsByCourseId(1)).thenReturn(Collections.singletonList(lesson));

    // Act & Assert
    mockMvc
        .perform(
            get("/course-service/lessons/course/{courseId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Lesson 1"));
  }
}
