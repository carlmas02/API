package com.learn.quizService.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
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

import com.learn.quizService.model.Answers;
import com.learn.quizService.model.Questions;
import com.learn.quizService.model.Quiz;
import com.learn.quizService.model.Result;
import com.learn.quizService.repository.AnswerRepository;
import com.learn.quizService.repository.QuestionRepository;
import com.learn.quizService.repository.QuizRepository;
import com.learn.quizService.repository.ResultRepository;

public class QuizServiceTests {

	@InjectMocks
	private QuizService quizService;
	
	@Mock
	private QuizRepository quizRepository;
	
	@Mock
    private AnswerRepository answerRepository;
	
	@Mock
    private QuestionRepository questionRepository;
	
	@Mock
    private ResultRepository resultRepository;
	
	private Quiz quiz;
	
	@BeforeEach
	public void setUp() {
        MockitoAnnotations.openMocks(this);
        quiz = new Quiz("1", "1", "Java Basics", LocalDateTime.now(), 10, "00:10:00");
    }
	
	@Test
	public void getAllQuizzesTests()
	{
		when(quizRepository.findAll()).thenReturn(Collections.singletonList(quiz));
		List<Quiz> quizzes = quizService.getAllQuizzes();
		
		assert quizzes.size() == 1;
		assert quizzes.get(0).getQuizId().equals("1");
	}
	
	@Test
	public void getQuizByIdTests()
	{
		when(quizRepository.findById("1")).thenReturn(Optional.of(quiz));
        Quiz quizById = quizService.getQuizById("1");
        assertNotNull(quizById);
        assert quizById.getQuizId().equals("1");
	}
	
	@Test
	public void getQuizzesByCourseIdTests()
	{
		when(quizRepository.findByCourseId("1")).thenReturn(Collections.singletonList(quiz));
        List<Quiz> quizzesByCourseId = quizService.getQuizzesByCourseId("1");
        
        assert quizzesByCourseId.size() == 1;
        assert quizzesByCourseId.get(0).getCourseId().equals("1");
	}
	
	@Test
	public void getQuizByQuizTitleTests()
	{
		when(quizRepository.findByQuizTitle("Java Basics")).thenReturn(Collections.singletonList(quiz));
        List<Quiz> quizByQuizTitle = quizService.getQuizByQuizTitle("Java Basics");
        
        assert quizByQuizTitle.size() == 1;
        assert quizByQuizTitle.get(0).getQuizTitle().equals("Java Basics");
	}
	
	@Test
	public void createQuizTests()
	{
		when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        Quiz createdQuiz = quizService.createQuiz(quiz);
        
        assertNotNull(createdQuiz);
        assert createdQuiz.getQuizId().equals("1");
	}
	
	@Test
	public void deleteQuizTests()
	{
		when(quizRepository.existsById("1")).thenReturn(true);
		doNothing().when(quizRepository).deleteById("1");
		boolean isDeleted = quizService.deleteQuiz("1");
		
		assert isDeleted;
		verify(quizRepository, times(1)).deleteById("1");
	}
	
	@Test
	public void updateQuizTests()
	{
		when(quizRepository.findById("1")).thenReturn(Optional.of(quiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        quiz.setQuizTitle("Updated Java Basics");
        boolean isUpdated = quizService.updateQuiz(quiz);

        assert isUpdated;
        verify(quizRepository, times(1)).save(any(Quiz.class));
	}
	
	@Test
	public void deleteQuizzesByCourseIdTests()
	{
		when(quizRepository.findByCourseId("1")).thenReturn(Collections.singletonList(quiz));
        doNothing().when(quizRepository).deleteAll(anyList());
        quizService.deleteQuizzesByCourseId("1");
        
        verify(quizRepository, times(1)).deleteAll(anyList());
	}
	
	@Test
	public void attemptQuizTests()
	{
		String quizId = "1";
		String questionId = "1";
		String answerText = "answer1";
        String studentId = "kushum@gmail.com";
        String email = "kushum@gmail.com";
        int points = 10;
        
        Questions question = new Questions();
        question.setQuestionId(questionId);
        question.setPoints(points);

        Answers correctAnswer = new Answers();
        correctAnswer.setQuestionId(questionId);
        correctAnswer.setAnswerText(answerText);
        correctAnswer.setIsCorrect(true);
        
        when(answerRepository.findByQuestionId(questionId)).thenReturn(Collections.singletonList(correctAnswer));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionRepository.findByQuizId(quizId)).thenReturn(Collections.singletonList(question));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(new Quiz(quizId, "1", "Java", LocalDateTime.now(), 1, "1h")));
        when(resultRepository.save(any(Result.class))).thenReturn(new Result());
        
        Result result = quizService.attemptQuiz(Collections.singletonMap(questionId, answerText), quizId, email);
        
        assert result.getMarksScored() == points;
        assert result.getStudentId().equals(studentId);
	}
	
}
