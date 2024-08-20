package com.learn.quizService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.quizService.model.Quiz;

@Repository
public interface QuizRepository extends MongoRepository<Quiz,String>{
	List<Quiz> findByCourseId(String courseId);
    List<Quiz> findByQuizTitle(String quizTitle);
}
