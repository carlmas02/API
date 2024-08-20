package com.learn.quizService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.quizService.model.Result;

@Repository
public interface ResultRepository extends MongoRepository<Result, String>{

	List<Result> findByStudentId(String studentId);
	List<Result> findByQuizId(String quizId);
	List<Result> findByStudentIdAndQuizId(String studentId, String quizId);
	List<Result> findByStudentIdAndCourseId(String studentId, String courseId);

}
