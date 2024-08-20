package com.courseservice.course_service.repository;

import com.courseservice.course_service.model.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Integer> {

  List<Lesson> findByCourseId(int courseId);
}
