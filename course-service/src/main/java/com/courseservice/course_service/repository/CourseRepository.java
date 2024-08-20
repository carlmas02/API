package com.courseservice.course_service.repository;

import com.courseservice.course_service.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
  List<Course> findByMentorEmail(String mentorEmail);

  public boolean existsByName(String courseName);

  List<Course> findByNameContainingIgnoreCase(String name);

  //  @Query(
  //      nativeQuery = true,
  //      value = "SELECT * FROM embedding ORDER BY embed <-> cast(? as vector) LIMIT 5")
  //  List<Course> findNearestNeighbors(String value);
  //  // @Query(nativeQuery = true, value = "SELECT * FROM course ORDER BY embedding <-> ?
  // \\:\\:vector
  //  // LIMIT 5")
  //  List<Course> findNearestNeighbors(String value);
}
