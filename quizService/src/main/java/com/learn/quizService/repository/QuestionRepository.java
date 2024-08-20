package com.learn.quizService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.quizService.model.Questions;

@Repository
public interface QuestionRepository extends MongoRepository<Questions, String>{

	List<Questions> findByQuizId(String quizId);
}
