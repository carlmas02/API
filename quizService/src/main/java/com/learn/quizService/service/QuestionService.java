package com.learn.quizService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.quizService.exception.InvalidQuizDataException;
import com.learn.quizService.model.Questions;
import com.learn.quizService.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	// Get all questions
	public List<Questions> getAllQuestions() {
        return questionRepository.findAll();
    }
	
	// Get questions by id
	public Optional<Questions> getQuestionById(String questionId) {
		Optional<Questions> question = questionRepository.findById(questionId);
		if(question.isPresent()) {
			return question;
		}
		throw new InvalidQuizDataException("Question not found");
	}
	
	// Get question by quiz id
	public List<Questions> getQuestionsByQuizId(String quizId) {
        List<Questions> questions = questionRepository.findByQuizId(quizId);
        if(questions.isEmpty()) {
        	throw new InvalidQuizDataException("No questions found for the given quiz");
        }
        return questions;
    }
	
	// Create a new question
	public Questions createQuestion(Questions question){
        return questionRepository.save(question);
    }
	
	// Delete question
	public boolean deleteQuestion(String questionId){
		boolean isDeleted = false;
		if(questionRepository.existsById(questionId))
		{
			questionRepository.deleteById(questionId);
			isDeleted = true;
		}
		else {
			throw new InvalidQuizDataException("Question not found to delete");
		}
		return isDeleted;
	}
	
	// Update a question
	public boolean updateQuestion(Questions question){
        Optional<Questions> optional = questionRepository.findById(question.getQuestionId());
        boolean isUpdated = false;
        if(optional.isPresent())
        {
        	Questions existingQuestion = optional.get();
            existingQuestion.setDescription(question.getDescription());
            existingQuestion.setType(question.getType());
            existingQuestion.setPoints(question.getPoints());
            questionRepository.save(existingQuestion);
            isUpdated = true;
        }
        else {
        	throw new InvalidQuizDataException("Question not found to update.");
        }
        return isUpdated;
    }
	
}
