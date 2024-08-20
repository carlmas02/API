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
import com.learn.quizService.model.Result;
import com.learn.quizService.service.ResultService;

@RestController
@RequestMapping("/api/results")
public class ResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	@Autowired
    private ResultService resultService;
	
	// get all results
    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
    	logger.info("Fetching all results");
        List<Result> results = resultService.getAllResults();
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

    // get result by result id
    @GetMapping("/{resultId}")
    public ResponseEntity<Result> getResultById(@PathVariable String resultId) {
    	logger.info("Fetching result with id: {}", resultId);
        Optional<Result> result = resultService.getResultById(resultId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Get results by Student Id
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getResultsByStudentId(@PathVariable String studentId) {
    	logger.info("Fetching results for student with id: {}", studentId);
        List<Result> results = resultService.getResultsByStudentId(studentId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
    
    // Get results by Quiz Id
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Result>> getResultsByQuizId(@PathVariable String quizId) {
    	logger.info("Fetching results for quiz with id: {}", quizId);
        List<Result> results = resultService.getResultsByQuizId(quizId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
    
    // Get results by Student Id and Quiz Id
    @GetMapping("/student/{studentId}/quiz/{quizId}")
    public ResponseEntity<List<Result>> getResultsByStudentIdAndQuizId(@PathVariable String studentId, @PathVariable String quizId) {
    	logger.info("Fetching results for student with id: {} and quiz with id: {}", studentId, quizId);
        List<Result> results = resultService.getResultsByStudentIdAndQuizId(studentId, quizId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
    
    // Get results by Student Id and Course Id
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<Result>> getResultsByStudentIdAndCourseId(@PathVariable String studentId, @PathVariable String courseId) {
    	logger.info("Fetching results for student with id: {} and course with id: {}", studentId, courseId);
        List<Result> results = resultService.getResultsByStudentIdAndCourseId(studentId, courseId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

    // creating or saving result
    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody Result result) throws EmptyRequestBodyException{
    	if(result == null) {
    		throw new EmptyRequestBodyException("Request body cannot be empty");
    	}
    	logger.info("Creating new result: {}", result);
        Result createdResult = resultService.saveResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
    }

    // deleting result
    @DeleteMapping("/{resultId}")
    public ResponseEntity<Void> deleteResult(@PathVariable String resultId) {
    	logger.info("Deleting result with id: {}", resultId);
        boolean isDeleted = resultService.deleteResult(resultId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // updating result
    @PutMapping("/{resultId}")
    public ResponseEntity<Boolean> updateResult(@PathVariable String resultId, @RequestBody Result result) throws EmptyRequestBodyException{
    	if(result == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty");
        }
    	logger.info("Updating result with id: {}", resultId);
        result.setResultId(resultId);
        boolean isUpdated = resultService.updateResult(result);
        if (isUpdated) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
