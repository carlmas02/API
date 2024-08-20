package com.learn.quizService.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.learn.quizService.model.Questions;
import com.learn.quizService.repository.QuestionRepository;

public class QuestionServiceTests {

	@InjectMocks
	private QuestionService questionService;
	
	@Mock
	private QuestionRepository questionRepository;
	
	private Questions question;
	
	@BeforeEach
	public void setUp() {
        MockitoAnnotations.openMocks(this);
        question = new Questions("1", "1", "What is Java?", "Text", 2);
	}
	
	@Test 
	public void getAllQuestionsTests() {
        when(questionRepository.findAll()).thenReturn(Collections.singletonList(question));
        List<Questions> questions = questionService.getAllQuestions();
        
        assert questions.size() == 1;
        assert questions.get(0).getQuestionId().equals("1");
    }
	
	@Test
	public void getQuestionByIdTests() {
		when(questionRepository.findById("1")).thenReturn(Optional.of(question));
		Optional<Questions> questionById = questionService.getQuestionById("1");
		
		assertNotNull(questionById);
		assert questionById.get().getQuestionId().equals("1");
	}
	
	@Test
	public void getQuestionsByQuizIdTests()
	{
		when(questionRepository.findByQuizId("1")).thenReturn(Collections.singletonList(question));
		List<Questions> questionsByQuizId = questionService.getQuestionsByQuizId("1");
        
        assert questionsByQuizId.size() == 1;
        assert questionsByQuizId.get(0).getQuestionId().equals("1");
	}
	
	@Test
	public void createQuestionTests()
	{
		when(questionRepository.save(any(Questions.class))).thenReturn(question);
		Questions createdQuestion = questionService.createQuestion(question);
		
        assertNotNull(createdQuestion);
		assert createdQuestion.getQuestionId().equals("1");
	}
	
	@Test
    public void deleteQuestionTests() {
		when(questionRepository.existsById("1")).thenReturn(true);
		doNothing().when(questionRepository).deleteById("1");
		boolean isDeleted = questionService.deleteQuestion("1");
		
        assert isDeleted;
        verify(questionRepository, times(1)).deleteById("1");
	}
	
	@Test
	public void updateQuestionTests() {
		when(questionRepository.findById("1")).thenReturn(Optional.of(question));
		when(questionRepository.save(any(Questions.class))).thenReturn(question);
		question.setDescription("Updated Java");
		
		boolean isUpdated = questionService.updateQuestion(question);
		
        assert isUpdated;
        verify(questionRepository, times(1)).save(any(Questions.class));
	}
}
