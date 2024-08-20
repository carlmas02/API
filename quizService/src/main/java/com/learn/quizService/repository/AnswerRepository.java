package com.learn.quizService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.quizService.model.Answers;

@Repository
public interface AnswerRepository extends MongoRepository<Answers,String> {

	List<Answers> findByQuestionId(String questionId);
}
