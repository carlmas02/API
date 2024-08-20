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
import com.learn.quizService.model.Answers;
import com.learn.quizService.service.AnswerService;

@WebMvcTest(AnswerController.class)
public class AnswerControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AnswerService answerService;
	
	@Autowired
	private AnswerController answerController;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private Answers answer1;
	private Answers answer2;
	
	@BeforeEach
	void setUp(){
		answer1 = new Answers("1", "1", "Correct Answer", true);
		MockitoAnnotations.openMocks(this);
		
        answer2 = new Answers("2", "2", "Incorrect Answer", false);
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
	}
	
	@Test
	void getAllAnswersTests() throws Exception {
		when(answerService.getAllAnswers()).thenReturn(List.of(answer1, answer2));
		
		mockMvc
		    .perform(get("/api/answers"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[0].answerId").value("1"))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[1].answerId").value("2"));	
    }
	
	@Test
	void getAnswerByIdTests() throws Exception {
		when(answerService.getAnswerById("1")).thenReturn(answer1);
        
        mockMvc.perform(get("/api/answers/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.answerId").value("1"));
	}
	
	@Test
	void getAnswerByIdNotFoundTests() throws Exception {
		when(answerService.getAnswerById("1")).thenReturn(null);
        
        mockMvc
            .perform(get("/api/answers/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void getAnswerByQuestionIdTests() throws Exception {
		when(answerService.getAnswersByQuestionId("1")).thenReturn(List.of(answer1));
		
		mockMvc
		    .perform(get("/api/answers/question?questionId=1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[0].answerId").value("1"));
	}
	
	@Test
    void getAnswerByQuestionIdNotFoundTests() throws Exception {
		when(answerService.getAnswersByQuestionId("1")).thenReturn(List.of());
		
		mockMvc
		    .perform(get("/api/answers/question?questionId=1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void getCorrectAnswerTests() throws Exception {
		when(answerService.getCorrectAnswer("1")).thenReturn(Optional.of(answer1));
        
        mockMvc
            .perform(get("/api/answers/correct?questionId=1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.answerId").value("1"));
	}
	
	@Test
    void getCorrectAnswerNotFoundTests() throws Exception {
		when(answerService.getCorrectAnswer("1")).thenReturn(Optional.empty());
		
		mockMvc
		    .perform(get("/api/answers/correct/question?questionId=1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void createAnswerTests() throws Exception {
		when(answerService.saveAnswer(any(Answers.class))).thenReturn(answer1);
		
		mockMvc.perform(post("/api/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answer1)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
                
	}
	
	@Test
	void deleteAnswerTests() throws Exception {
		when(answerService.deleteAnswer("1")).thenReturn(true);
		
		mockMvc
		    .perform(delete("/api/answers/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
    void deleteAnswerNotFoundTests() throws Exception {
		
		when(answerService.deleteAnswer("1")).thenReturn(false);
		
		mockMvc
		    .perform(delete("/api/answers/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
    void updateAnswerTests() throws Exception {
		when(answerService.updateAnswer(any(Answers.class))).thenReturn(true);
		
		mockMvc.perform(put("/api/answers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answer1)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
    void updateAnswerNotFoundTests() throws Exception {
		
        when(answerService.updateAnswer(any(Answers.class))).thenReturn(false);
        
        mockMvc.perform(put("/api/answers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answer1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
