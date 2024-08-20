package com.learn.quizService.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.quizService.model.Questions;
import com.learn.quizService.service.QuestionService;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private QuestionService questionService;
	
	@Autowired
    private QuestionController questionController;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private Questions question1;
	private Questions question2;
	
	@BeforeEach
	void setUp(){
		question1 = new Questions("1", "1", "What is Java?", "Text", 2);
		MockitoAnnotations.openMocks(this);
		
		question2 = new Questions("2", "2", "What is Python?", "Text", 3);
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }
	
	@Test
	void getAllQuestionsTests() throws Exception {
		when(questionService.getAllQuestions()).thenReturn(List.of(question1, question2));
		
		mockMvc.perform(get("/api/questions"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[0].questionId").value("1"))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[1].questionId").value("2"));
	}
	
	@Test
	void getQuestionByIdTests() throws Exception {
		when(questionService.getQuestionById("1")).thenReturn(Optional.of(question1));
        
        mockMvc.perform(get("/api/questions/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.questionId").value("1"));
	}
	
	@Test
	void getQuestionByIdNotFoundTests() throws Exception {
		when(questionService.getQuestionById("1")).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/questions/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void getQuestionByQuizIdTests() throws Exception {
		when(questionService.getQuestionsByQuizId("1")).thenReturn(List.of(question1));
        
        mockMvc.perform(get("/api/questions/quiz/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].questionId").value("1"));
	}
	
	@Test
	void getQuestionByQuizIdNotFoundTests() throws Exception {
		when(questionService.getQuestionsByQuizId("1")).thenReturn(List.of());
        
        mockMvc.perform(get("/api/questions/quiz/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
    void createQuestionTests() throws Exception {
		when(questionService.createQuestion(question1)).thenReturn(question1);
		
		mockMvc.perform(post("/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(question1)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void updateQuestionTests() throws Exception {
		when(questionService.updateQuestion(any(Questions.class))).thenReturn(true);

        mockMvc.perform(put("/api/questions/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(question1)))
            .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void updateQuestionNotFoundTests() throws Exception{
		when(questionService.updateQuestion(question1)).thenReturn(false);
        
        mockMvc.perform(put("/api/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(question1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
    void deleteQuestionTests() throws Exception {
		when(questionService.deleteQuestion("1")).thenReturn(true);
		
		mockMvc.perform(delete("/api/questions/1"))
			.andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNoContent());	
	}
	
	@Test
    void deleteQuestionNotFoundTests() throws Exception {
		when(questionService.deleteQuestion("1")).thenReturn(false);
		
		mockMvc.perform(delete("/api/questions/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
