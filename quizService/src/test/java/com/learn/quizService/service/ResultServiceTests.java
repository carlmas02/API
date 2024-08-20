package com.learn.quizService.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.learn.quizService.model.Result;
import com.learn.quizService.repository.ResultRepository;

public class ResultServiceTests {

	@InjectMocks
	private ResultService resultService;
	
	@Mock
    private ResultRepository resultRepository;
	
	private Result result;
	
	@BeforeEach
    public void setUp() {
		MockitoAnnotations.openMocks(this);
		result = new Result("1", "kushum@gmail.com", "1", "1", LocalDateTime.now(), 50, 70);
    }
	
	@Test
	public void getAllResultsTests() {
		when(resultRepository.findAll()).thenReturn(Collections.singletonList(result));
        List<Result> results = resultService.getAllResults();
        
        assert results.size() == 1;
        assert results.get(0).getResultId().equals("1");
	}
	
	@Test
	public void getResultByIdTests() {
		when(resultRepository.findById("1")).thenReturn(Optional.of(result));
		Optional<Result> resultById = resultService.getResultById("1");
		
		assert resultById.isPresent();
		assert resultById.get().getResultId().equals("1");
	}
	
	@Test
	public void getResultsByStudentIdTests()
	{
		when(resultRepository.findByStudentId("kushum@gmail.com")).thenReturn(Collections.singletonList(result));
        List<Result> existingResult = resultService.getResultsByStudentId("kushum@gmail.com");
        
        assert existingResult.size() == 1;
        assert existingResult.get(0).getStudentId().equals("kushum@gmail.com");
	}
	
	@Test
	public void getResultsByQuizIdTests()
	{
		when(resultRepository.findByQuizId("1")).thenReturn(Collections.singletonList(result));
        List<Result> existingResult = resultService.getResultsByQuizId("1");
        
        assert existingResult.size() == 1;
        assert existingResult.get(0).getQuizId().equals("1");
	}
	
	@Test
	public void getResultsByStudentIdAndQuizIdTests()
	{
		when(resultRepository.findByStudentIdAndQuizId("kushum@gmail.com", "1")).thenReturn(Collections.singletonList(result));
		List<Result> existingResult = resultService.getResultsByStudentIdAndQuizId("kushum@gmail.com", "1");
		
		assert existingResult.size() == 1;
		assert existingResult.get(0).getStudentId().equals("kushum@gmail.com");
		assert existingResult.get(0).getQuizId().equals("1");
	}
	
	@Test
    public void getResultsByStudentIdAndCourseIdTests() {
		when(resultRepository.findByStudentIdAndCourseId("kushum@gmail.com", "1")).thenReturn(Collections.singletonList(result));
		List<Result> existingResult = resultService.getResultsByStudentIdAndCourseId("kushum@gmail.com", "1");
		
		assert existingResult.size() == 1;
		assert existingResult.get(0).getStudentId().equals("kushum@gmail.com");
		assert existingResult.get(0).getCourseId().equals("1");
	}
	
	@Test
	public void saveResultTests()
	{
		when(resultRepository.save(any(Result.class))).thenReturn(result);
		Result savedResult = resultService.saveResult(result);
		
		assertNotNull(savedResult);
		assert savedResult.getResultId().equals("1");
	}
	
	@Test
	public void deleteResultTests()
	{
		when(resultRepository.existsById("1")).thenReturn(true);
        
		boolean isDeleted = resultService.deleteResult("1");
		
        assert isDeleted;
        verify(resultRepository, times(1)).deleteById("1");
		
	}
	
	@Test
	public void updateResultTests()
	{
		when(resultRepository.findById("1")).thenReturn(Optional.of(result));
		when(resultRepository.save(any(Result.class))).thenReturn(result);
		
		result.setMarksScored(68);
		boolean isUpdated = resultService.updateResult(result);
		
        assert isUpdated;
        verify(resultRepository, times(1)).save(any(Result.class));
	}
}
