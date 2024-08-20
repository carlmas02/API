package com.courseservice.course_service.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.courseservice.course_service.model.Course;
import com.courseservice.course_service.model.Lesson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

  @Autowired private CourseRepository courseRepository;
  private Course course1;
  private Course course2;

  @BeforeEach
  void setUp() throws Exception {

    List<Lesson> lessons = new ArrayList<>();
    courseRepository.deleteAll();
    course1 =
        new Course(
            1,
            "Python",
            "A beginner to learning python",
            new Date(),
            new Date(),
            "carl@gmail.com",
            lessons);

    course2 =
        new Course(
            1,
            "Javascript",
            "A beginner to learning js",
            new Date(),
            new Date(),
            "carl@gmail.com",
            lessons);
  }

  @AfterEach
  void tearDown() throws Exception {
    course1 = null;
    course2 = null;
  }

  @Test
  public void testSaveCourseSuccess() {
    Course savedCourse = courseRepository.save(course1);
    assertThat(savedCourse).isNotNull();
    assertThat(savedCourse.getName()).isNotNull();
    assertThat(savedCourse.getDescription()).isEqualTo("A beginner to learning python");
    assertThat(savedCourse.getLessons()).hasSize(0);
    assertThat(savedCourse.getMentorEmail()).isEqualTo("carl@gmail.com");
    assertThat(savedCourse.getCreatedAt()).isNotNull();
  }

  @Test
  public void testDeleteCourseSuccess() {
    Course courseSaved = courseRepository.save(course1);

    boolean isExists = courseRepository.existsByName(courseSaved.getName());
    assertTrue(isExists);
  }

  @Test
  public void testUpdateCourse() {

    Course savedCourse = courseRepository.save(course1);

    // When
    savedCourse.setName("Advanced Python");
    courseRepository.save(savedCourse);
    Course updatedCourse = courseRepository.findById(savedCourse.getId()).orElse(null);

    // Then
    assertThat(updatedCourse).isNotNull();
    assertThat(updatedCourse.getName()).isEqualTo("Advanced Python");
  }

  @Test
  public void testDeleteCourse() {
    Course savedCourse = courseRepository.save(course1);
    // When
    courseRepository.delete(savedCourse);
    Course foundCourse = courseRepository.findById(savedCourse.getId()).orElse(null);
    // Then
    assertThat(foundCourse).isNull();
  }

  @Test
  public void testFindAllCourses() {

    courseRepository.save(course1);
    courseRepository.save(course2);

    // When
    List<Course> courses = courseRepository.findAll();

    // Then
    assertThat(courses).hasSize(2);
    assertThat(courses).extracting(Course::getName).containsExactlyInAnyOrder("Python", "Javascript");
  }
}
