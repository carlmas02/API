package com.courseservice.course_service.service;

import com.courseservice.course_service.exceptions.EntityIdNotExistException;
import com.courseservice.course_service.exceptions.ImmutableFieldUpdateException;
import com.courseservice.course_service.exceptions.InvalidCourseDataException;
import com.courseservice.course_service.model.Course;
import com.courseservice.course_service.repository.CourseRepository;
// TODO ADD CIRCUIT BREAKER IF TIME PERSISTS
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  private final RestTemplate restTemplate;

  public CourseServiceImpl(CourseRepository courseRepository, RestTemplate restTemplate) {
    this.courseRepository = courseRepository;
    this.restTemplate = restTemplate;
  }

  // Create a new course
  // @Transactional
  public Course createCourse(Course course) throws InvalidCourseDataException {

    if (course.getName() == null || course.getName().isEmpty()) {
      throw new InvalidCourseDataException("Course name cannot be null or empty");
    }

    if (course.getMentorEmail() == null || course.getMentorEmail().isEmpty()) {
      throw new InvalidCourseDataException("Mentor email cannot be null or empty");
    }

    if (course.getId() != 0) {
      throw new InvalidCourseDataException("Invalid data passed, id must not be specified");
    }

//    if (!verifyMentorEmail(course.getMentorEmail())) {
//      throw new InvalidCourseDataException("Mentor email is not valid");
//    }

    course.setCreatedAt(new Date());
    course.setUpdatedAt(new Date());
    return courseRepository.save(course);
  }

  // Get a course by ID
  public Optional<Course> getCourseById(int id) {
    return courseRepository.findById(id);
  }

  // Get all courses
  public List<Course> getAllCourses() {
    return courseRepository.findAll();
  }

  // Update an existing course
  // @Transactional
  public Optional<Course> updateCourse(int id, Course updatedCourse)
      throws ImmutableFieldUpdateException {

    if (updatedCourse.getMentorEmail() != null) {
      throw new ImmutableFieldUpdateException("Mentor email cannot be updated");
    }

    return courseRepository
        .findById(id)
        .map(
            course -> {
              course.setName(updatedCourse.getName());
              course.setDescription(updatedCourse.getDescription());
              course.setUpdatedAt(new Date());
              return courseRepository.save(course);
            });
  }

  // Delete a course by ID
  public boolean deleteCourse(int id) throws EntityIdNotExistException {
    if (courseRepository.existsById(id)) {
      courseRepository.deleteById(id);
      restTemplate.delete("http://QUIZSERVICE/api/quizzes/course/" + id);
      return true;
    } else {
      throw new EntityIdNotExistException("Course ID does not exist");
    }
  }

  // Get courses by mentor's email
  public List<Course> getCoursesByMentorEmail(String mentorEmail) {
    return courseRepository.findByMentorEmail(mentorEmail);
  }

  public List<Course> getCoursesByName(String name) {
    return courseRepository.findByNameContainingIgnoreCase(name);
  }

  @Override
  public boolean verifyMentorEmail(String mentorEmail) {
    String url = "http://USER-SERVICE/user-service/api/instructors/email/{email}";
    try {
      String output = restTemplate.getForObject(url, String.class, mentorEmail);
      return true;
//      return output != null && output;
    } catch (HttpClientErrorException e) {
      return false;
    }
  }
}
