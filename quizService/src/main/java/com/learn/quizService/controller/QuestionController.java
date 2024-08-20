package com.learn.quizService.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.quizService.exception.EmptyRequestBodyException;
import com.learn.quizService.model.Questions;
import com.learn.quizService.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService questionService;
	
	// Get all questions
	@GetMapping
    public ResponseEntity<List<Questions>> getAllQuestions() {
		logger.info("Fetching all questions");
		List<Questions> questions = questionService.getAllQuestions();
		return ResponseEntity.ok(questions);
	}
	
	// Get all questions by quiz id
	@GetMapping("/quiz/{quizId}")
	public ResponseEntity<List<Questions>> getAllQuestions(@PathVariable String quizId) {
		logger.info("Fetching questions for quiz with id: {}", quizId);
        List<Questions> questions = questionService.getQuestionsByQuizId(quizId);
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }
	
	// Get question by question id
	@GetMapping("/{questionId}")
    public ResponseEntity<Questions> getQuestionById(@PathVariable String questionId) {
		logger.info("Fetching question with id: {}", questionId);
        Optional<Questions> question = questionService.getQuestionById(questionId);
        return question.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	// Create a new question
	@PostMapping
    public ResponseEntity<Questions> createQuestion(@RequestBody Questions question) throws EmptyRequestBodyException {
		if(question == null) {
			throw new EmptyRequestBodyException("Request body cannot be empty. Provide question details.");
		}
		logger.info("Creating new question: {}", question);
        Questions createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }
	
	// Delete a question
	@DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String questionId) {
		logger.info("Deleting question with id: {}", questionId);
        if (questionService.deleteQuestion(questionId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
	
	// Update a question
	@PutMapping("/{questionId}")
    public ResponseEntity<Void> updateQuestion(@PathVariable String questionId, @RequestBody Questions question) {
		if(question == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty");
        }
		logger.info("Updating question with id: {}", questionId);
        question.setQuestionId(questionId);
        if (questionService.updateQuestion(question)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
	
}
