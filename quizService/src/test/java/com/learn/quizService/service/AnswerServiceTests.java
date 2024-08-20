package com.learn.quizService.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.learn.quizService.model.Answers;
import com.learn.quizService.repository.AnswerRepository;

public class AnswerServiceTests {

	@InjectMocks
	private AnswerService answerService;
	
	@Mock
    private AnswerRepository answerRepository;
	
	private Answers answers;
	
	@BeforeEach
	public void setUp() {
        MockitoAnnotations.openMocks(this);
        answers = new Answers("1", "1", "answer", true);
    }
	
	@Test
	public void getAllAnswersTests() {
		when(answerRepository.findAll()).thenReturn(Collections.singletonList(answers));
		List<Answers> allAnswers = answerService.getAllAnswers();
		
		assert allAnswers.size() == 1;
		assert allAnswers.get(0).getAnswerId().equals("1");
	}
	
	@Test
	public void getAnswerByIdTests() {
		when(answerRepository.findById("1")).thenReturn(Optional.of(answers));
        Answers answerById = answerService.getAnswerById("1");
        
        assertNotNull(answerById);
        assert answerById.getAnswerId().equals("1");
	}
	
	@Test
	public void getAnswersByQuestionIdTests() {
		when(answerRepository.findByQuestionId("1")).thenReturn(Collections.singletonList(answers));
		List<Answers> existingAnswer = answerService.getAnswersByQuestionId("1");
		
		assert existingAnswer.size() == 1;
		assert existingAnswer.get(0).getQuestionId().equals("1");
	}
}
