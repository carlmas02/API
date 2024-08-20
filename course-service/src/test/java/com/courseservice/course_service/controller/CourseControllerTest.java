package com.courseservice.course_service.controller;

import com.courseservice.course_service.model.Course;
import com.courseservice.course_service.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CourseService courseService;

  @MockBean private RestTemplate restTemplate;

  @Autowired private CourseController courseController;

  private Course course1;
  private Course course2;

  @BeforeEach
  void setUp() throws Exception {
    course1 =
        new Course(
            1,
            "Python",
            "A beginner to learning python",
            new Date(),
            new Date(),
            "carl@gmail.com",
            new ArrayList<>());

    MockitoAnnotations.openMocks(this);

    course2 =
        new Course(
            2,
            "Javascript",
            "A beginner to learning Js",
            new Date(),
            new Date(),
            "ashley@gmail.com",
            new ArrayList<>());

    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
  }

  @Test
  void testGetAllCourses() throws Exception {
    List<Course> courseList = List.of(course1, course2);
    when(courseService.getAllCourses()).thenReturn(courseList);

    mockMvc
        .perform(get("/course-service/courses"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Python"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Javascript"));
  }

  @Test
  void createCourse() throws Exception {
    when(courseService.createCourse(course1)).thenReturn(course1);

    String resultStr = new ObjectMapper().writeValueAsString(course1);

    mockMvc
        .perform(post("/course-service/courses").contentType(MediaType.APPLICATION_JSON).content(resultStr))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void getCourseByIdSuccess() throws Exception {
    when(courseService.getCourseById(1)).thenReturn(Optional.of(course1));
    String resultStr = new ObjectMapper().writeValueAsString(course1);
    mockMvc
        .perform(get("/course-service/courses/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(resultStr));
  }

  @Test
  void getCourseByIdFailure() throws Exception {
    when(courseService.getCourseById(1000)).thenReturn(Optional.empty());
    mockMvc
        .perform(get("/course-service/courses/1000"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void getAllCourses() {
    List<Course> courseList = List.of(course1, course2);
    when(courseService.getAllCourses()).thenReturn(courseList);
  }

//  @Test .
//  void updateCourse() throws Exception {
//    String resultStr = new ObjectMapper().writeValueAsString(course1);
//    when(courseService.updateCourse(1, course1)).thenReturn(Optional.of(course1));
//    mockMvc
//        .perform(put("/course-service/courses/1").contentType(MediaType.APPLICATION_JSON).content(resultStr))
//        .andDo(MockMvcResultHandlers.print())
//        .andExpect(MockMvcResultMatchers.status().isOk());
//  }

  @Test
  void deleteCourseSuccess() throws Exception {
    when(courseService.deleteCourse(1)).thenReturn(true);
    mockMvc
        .perform(delete("/course-service/courses/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void deleteCourseFailure() throws Exception {
    when(courseService.deleteCourse(1111)).thenReturn(false);
    mockMvc
        .perform(delete("/course-service/courses/1111"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void getCoursesByMentorEmail() throws Exception {
    List<Course> courseList = List.of(course1);
    when(courseService.getCoursesByMentorEmail("carl")).thenReturn(courseList);
    mockMvc
        .perform(get("/course-service/courses/mentor/?mentorEmail=carl"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Python"));
  }
}
