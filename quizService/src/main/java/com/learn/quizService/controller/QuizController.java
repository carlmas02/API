package com.learn.quizService.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.quizService.exception.EmptyRequestBodyException;
import com.learn.quizService.model.AttemptQuizRequest;
import com.learn.quizService.model.Quiz;
import com.learn.quizService.service.QuizService;

@RestController
@RequestMapping("api/quizzes")
public class QuizController {
	
	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
	
	@Autowired
	private QuizService quizService;
	
	// Get all quizzes
	@GetMapping
	public ResponseEntity<List<Quiz>> getAllQuizzes() {
		logger.info("Fetching all quizzes");
		List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }
	
	// Get quiz by id
	@GetMapping("/{quizId}")
	public ResponseEntity<Quiz> getQuizById(@PathVariable String quizId) {
		logger.info("Fetching quiz with id: {}", quizId);
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quiz);
    }
	
	// Get quiz by course id
	@GetMapping("/course/{courseId}")
	public ResponseEntity<List<Quiz>> getQuizzesByCourseId(@PathVariable String courseId) {
		logger.info("Fetching quizzes for course with id: {}", courseId);
        List<Quiz> quizzes = quizService.getQuizzesByCourseId(courseId);
        if (quizzes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizzes);
    }
	
	// Get quiz by quiz title
	@GetMapping("/title/{quizTitle}")
    public ResponseEntity<List<Quiz>> getQuizzesByTitle(@PathVariable String quizTitle) {
		logger.info("Fetching quizzes with title: {}", quizTitle);
        List<Quiz> quizzes = quizService.getQuizByQuizTitle(quizTitle);
        if (quizzes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizzes);
    }
	
	// Create a new quiz
	@PostMapping
	public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) throws EmptyRequestBodyException {
		if(quiz == null) {
			throw new EmptyRequestBodyException("Request body cannot be empty. Provide quiz details.");
		}
		logger.info("Creating new quiz: {}", quiz);
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }
	
	// Delete a quiz
	@DeleteMapping("/{quizId}")
	public ResponseEntity<Void> deleteQuiz(@PathVariable String quizId) {
		logger.info("Deleting quiz with id: {}", quizId);
        boolean isDeleted = quizService.deleteQuiz(quizId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	// Update a quiz
	@PutMapping("/{quizId}")
    public ResponseEntity<Void> updateQuiz(@PathVariable String quizId, @RequestBody Quiz quiz) {
		if(quiz == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty");
        }
		logger.info("Updating quiz with id: {}", quizId);
		boolean isUpdated = quizService.updateQuiz(quiz);
		return isUpdated? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
	
	// Delete all quizzes by course id
	@DeleteMapping("/course/{courseId}")
	public ResponseEntity<Void> deleteQuizzesByCourseId(@PathVariable String courseId) {
		logger.info("Deleting all quizzes for course with id: {}", courseId);
        quizService.deleteQuizzesByCourseId(courseId);
        return ResponseEntity.noContent().build();
    }

	// Attempt a quiz
	@PostMapping("/attempt")
    public ResponseEntity<Void> attemptQuiz(@RequestBody AttemptQuizRequest request, @RequestHeader String email) {
		if(request == null || request.getAnswers() == null || request.getQuizId() == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty");
        }
		logger.info("Attempting quiz for user: {}", email);
        quizService.attemptQuiz(request.getAnswers(), request.getQuizId(), email);
        return ResponseEntity.ok().build();
    }

}