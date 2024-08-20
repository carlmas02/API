package com.courseservice.course_service.service;

import com.courseservice.course_service.exceptions.EntityIdNotExistException;
import com.courseservice.course_service.exceptions.ImmutableFieldUpdateException;
import com.courseservice.course_service.exceptions.InvalidCourseDataException;
import com.courseservice.course_service.model.Course;
import com.courseservice.course_service.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

  @Mock private CourseRepository courseRepository;

  @Mock private RestTemplate restTemplate;

  @InjectMocks private CourseServiceImpl courseService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateCourse_Success() throws InvalidCourseDataException {
    // Arrange
    Course course = new Course();
    course.setName("Java Basics");
    course.setMentorEmail("mentor@example.com");

    when(courseRepository.save(any(Course.class))).thenReturn(course);
    when(restTemplate.getForObject(anyString(), eq(Boolean.class), anyString())).thenReturn(true);

    // Act
    Course createdCourse = courseService.createCourse(course);

    // Assert
    assertNotNull(createdCourse);
    assertEquals("Java Basics", createdCourse.getName());
    assertNotNull(createdCourse.getCreatedAt());
    assertNotNull(createdCourse.getUpdatedAt());
    verify(courseRepository, times(1)).save(course);
  }

  @Test
  public void testCreateCourse_InvalidData() {
    // Arrange
    Course course = new Course();
    course.setMentorEmail("mentor@example.com");

    // Act & Assert
    assertThrows(InvalidCourseDataException.class, () -> courseService.createCourse(course));
  }

  @Test
  public void testCreateCourse_InvalidMentorEmail() {
    // Arrange
    Course course = new Course();
    course.setName("Java Basics");
    course.setMentorEmail("invalid@example.com");

    when(restTemplate.getForObject(anyString(), eq(Boolean.class), anyString())).thenReturn(false);

    // Act & Assert
    assertThrows(InvalidCourseDataException.class, () -> courseService.createCourse(course));
  }

  @Test
  public void testGetCourseById() {
    // Arrange
    Course course = new Course();
    course.setId(1);
    course.setName("Java Basics");

    when(courseRepository.findById(1)).thenReturn(Optional.of(course));

    // Act
    Optional<Course> result = courseService.getCourseById(1);

    // Assert
    assertTrue(result.isPresent());
    assertEquals("Java Basics", result.get().getName());
    verify(courseRepository, times(1)).findById(1);
  }

  @Test
  public void testGetAllCourses() {
    // Arrange
    Course course = new Course();
    course.setName("Java Basics");

    when(courseRepository.findAll()).thenReturn(List.of(course));

    // Act
    List<Course> result = courseService.getAllCourses();

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    verify(courseRepository, times(1)).findAll();
  }

  @Test
  public void testUpdateCourse_Success() throws ImmutableFieldUpdateException {
    // Arrange
    Course existingCourse = new Course();
    existingCourse.setId(1);
    existingCourse.setName("Java Basics");
    existingCourse.setMentorEmail("mentor@example.com");

    Course updatedCourse = new Course();
    updatedCourse.setName("Advanced Java");

    when(courseRepository.findById(1)).thenReturn(Optional.of(existingCourse));
    when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

    // Act
    Optional<Course> result = courseService.updateCourse(1, updatedCourse);

    // Assert
    assertTrue(result.isPresent());
    assertEquals("Advanced Java", result.get().getName());
    verify(courseRepository, times(1)).findById(1);
    verify(courseRepository, times(1)).save(existingCourse);
  }

  @Test
  public void testUpdateCourse_ImmutableFieldUpdate() {
    // Arrange
    Course updatedCourse = new Course();
    updatedCourse.setMentorEmail("newmentor@example.com");

    // Act & Assert
    assertThrows(
        ImmutableFieldUpdateException.class, () -> courseService.updateCourse(1, updatedCourse));
  }

  @Test
  public void testDeleteCourse_Success() throws EntityIdNotExistException {
    // Arrange
    when(courseRepository.existsById(1)).thenReturn(true);

    // Act
    boolean result = courseService.deleteCourse(1);

    // Assert
    assertTrue(result);
    verify(courseRepository, times(1)).deleteById(1);
    verify(restTemplate, times(1)).delete("http://QUIZSERVICE/api/quizzes/course/1");
  }

  @Test
  public void testDeleteCourse_EntityIdNotExist() {
    // Arrange
    when(courseRepository.existsById(1)).thenReturn(false);

    // Act & Assert
    assertThrows(EntityIdNotExistException.class, () -> courseService.deleteCourse(1));
  }

  @Test
  public void testGetCoursesByMentorEmail() {
    // Arrange
    Course course = new Course();
    course.setName("Java Basics");

    when(courseRepository.findByMentorEmail("mentor@example.com")).thenReturn(List.of(course));

    // Act
    List<Course> result = courseService.getCoursesByMentorEmail("mentor@example.com");

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    verify(courseRepository, times(1)).findByMentorEmail("mentor@example.com");
  }

  @Test
  public void testVerifyMentorEmail_Valid() {
    // Arrange
    String mentorEmail = "mentor@example.com";
    when(restTemplate.getForObject(anyString(), eq(Boolean.class), eq(mentorEmail)))
        .thenReturn(true);

    // Act
    boolean result = courseService.verifyMentorEmail(mentorEmail);

    // Assert
    assertTrue(result);
    verify(restTemplate, times(1)).getForObject(anyString(), eq(Boolean.class), eq(mentorEmail));
  }

  @Test
  public void testVerifyMentorEmail_Invalid() {
    // Arrange
    String mentorEmail = "mentor@example.com";
    when(restTemplate.getForObject(anyString(), eq(Boolean.class), eq(mentorEmail)))
        .thenReturn(false);

    // Act
    boolean result = courseService.verifyMentorEmail(mentorEmail);

    // Assert
    assertFalse(result);
    verify(restTemplate, times(1)).getForObject(anyString(), eq(Boolean.class), eq(mentorEmail));
  }

  @Test
  public void testVerifyMentorEmail_Exception() {
    // Arrange
    String mentorEmail = "mentor@example.com";
    when(restTemplate.getForObject(anyString(), eq(Boolean.class), eq(mentorEmail)))
        .thenThrow(HttpClientErrorException.class);

    // Act
    boolean result = courseService.verifyMentorEmail(mentorEmail);

    // Assert
    assertFalse(result);
    verify(restTemplate, times(1)).getForObject(anyString(), eq(Boolean.class), eq(mentorEmail));
  }
}
