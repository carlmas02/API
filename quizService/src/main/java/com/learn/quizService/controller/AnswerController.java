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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.quizService.exception.EmptyRequestBodyException;
import com.learn.quizService.model.Answers;
import com.learn.quizService.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {
	
	private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);
	
	@Autowired
	private AnswerService answerService;
	
	// Get all answers
	@GetMapping
	public ResponseEntity<List<Answers>> getAllAnswers() {
		logger.info("Fetching all answers");
		List<Answers> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }
	
	// Get all answers by question id 
	@GetMapping("/question")
	public ResponseEntity<List<Answers>> getAnswersByQuestionId(@RequestParam String questionId) {
		logger.info("Fetching answers for question with id: {}", questionId);
        List<Answers> answers = answerService.getAnswersByQuestionId(questionId);
        if (answers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(answers);
    }
	
	// Get correct answer to a given question
	@GetMapping("/correct")
    public ResponseEntity<Answers> getCorrectAnswer(@RequestParam String questionId) {
		logger.info("Fetching correct answer for question with id: {}", questionId);
		Optional<Answers> correctAnswer = answerService.getCorrectAnswer(questionId);
        return correctAnswer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	// Get answer by answer id
	@GetMapping("/{answerId}")
    public ResponseEntity<Answers> getAnswerById(@PathVariable String answerId) {
		logger.info("Fetching answer with id: {}", answerId);
        Answers answer = answerService.getAnswerById(answerId);
        if(answer!=null) {
        	return ResponseEntity.ok(answer);
        }
        else
        {
        	return ResponseEntity.notFound().build();
        }
    }
	
	// Save answer
	@PostMapping
    public ResponseEntity<Answers> createAnswer(@RequestBody Answers answer) throws EmptyRequestBodyException{
		if(answer == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty. Provide answer details.");
        }
		logger.info("Creating new answer: {}", answer);
        Answers createdAnswer = answerService.saveAnswer(answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }
	
	// Delete answer
	@DeleteMapping("/{answerId}")
	public ResponseEntity<Void> deleteAnswer(@PathVariable String answerId) {
		logger.info("Deleting answer with id: {}", answerId);
        if (answerService.deleteAnswer(answerId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
	
	// Update answer
	@PutMapping("/{answerId}")
    public ResponseEntity<Void> updateAnswer(@PathVariable String answerId, @RequestBody Answers answer) throws EmptyRequestBodyException{
		if(answer == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty. Provide answer details.");
        }
		logger.info("Updating answer with id: {}", answerId);
        answer.setAnswerId(answerId);
        if (answerService.updateAnswer(answer)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
