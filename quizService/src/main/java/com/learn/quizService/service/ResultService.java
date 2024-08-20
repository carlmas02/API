package com.learn.quizService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.quizService.exception.InvalidQuizDataException;
import com.learn.quizService.model.Result;
import com.learn.quizService.repository.ResultRepository;

@Service
public class ResultService {

	@Autowired
	private ResultRepository resultRepository;
	
	// Get all results
	public List<Result> getAllResults() {
        return resultRepository.findAll();
    }
	
	//Get result by result id
	public Optional<Result> getResultById(String id) {
		Optional<Result> result = resultRepository.findById(id);
		if(result.isEmpty()) {
			throw new InvalidQuizDataException("No result with given result id");
		}
        return result;
    }
	
	// Get results by Student Id
	public List<Result> getResultsByStudentId(String studentId) {
		List<Result> results = resultRepository.findByStudentId(studentId);
		if(results.isEmpty()) {
			throw new InvalidQuizDataException("Student has not attempted any quiz.");
		}
        return results;
    }
	
	// Get results by Quiz Id
	public List<Result> getResultsByQuizId(String quizId) {
		List<Result> results = resultRepository.findByQuizId(quizId);
		if(results.isEmpty()) {
            throw new InvalidQuizDataException("No result found for the given quiz");
        }
        return results;
    }
	
	// Get results by Student Id and Quiz Id
	public List<Result> getResultsByStudentIdAndQuizId(String studentId, String quizId) {
		List<Result> results = resultRepository.findByStudentIdAndQuizId(studentId, quizId);
		if(results.isEmpty()) {
            throw new InvalidQuizDataException("No result found for the given student and quiz");
        }
        return results;
    }
	
	//Get results by student Id and course id
	public List<Result> getResultsByStudentIdAndCourseId(String studentId, String courseId) {
		List<Result> results = resultRepository.findByStudentIdAndCourseId(studentId, courseId);
		if(results.isEmpty()) {
            throw new InvalidQuizDataException("No result found for the given student and course");
        }
        return results;
    }
	
	// Save a result
	public Result saveResult(Result result) {
        return resultRepository.save(result);
    }
	
	// Delete a result
	public boolean deleteResult(String resultId) {
		boolean isDeleted = false;
        if(resultRepository.existsById(resultId))
        {
            resultRepository.deleteById(resultId);
            isDeleted = true;
        }
        else {
        	throw new InvalidQuizDataException("Result not found to delete.");
        }
        return isDeleted;
	}
	
	// Update a result
	public boolean updateResult(Result result) {
		boolean isUpdated = false;
	    Optional<Result> optional = resultRepository.findById(result.getResultId());
	    if (optional.isPresent()) {
	        Result existingResult = optional.get();
	        existingResult.setTotalMarks(result.getTotalMarks());
	        resultRepository.save(existingResult);
	        isUpdated = true;
	    }
	    else {
	    	throw new InvalidQuizDataException("Result not found to update.");
	    }
	    return isUpdated;
	}

}

