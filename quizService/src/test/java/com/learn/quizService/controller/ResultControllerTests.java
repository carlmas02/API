package com.learn.quizService.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDateTime;
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
import com.learn.quizService.model.Result;
import com.learn.quizService.service.ResultService;

@WebMvcTest(ResultController.class)
public class ResultControllerTests {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private ResultService resultService;
	
	@Autowired
	private ResultController resultController;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private Result result1;
	private Result result2;
	
	@BeforeEach
	void setUp(){
        result1 = new Result("1", "kushum@gmail.com", "1", "1", LocalDateTime.now(), 10, 20);
        MockitoAnnotations.openMocks(this);
        
        result2 = new Result("2", "kusum@gmail.com", "2", "2", LocalDateTime.now(), 15, 20);
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(resultController).build();
    }
	
	@Test
    void getAllResultsTests() throws Exception {
		when(resultService.getAllResults()).thenReturn(List.of(result1, result2));
		
		mockMvc
		    .perform(get("/api/results"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[0].resultId").value("1"))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$[1].resultId").value("2"));
	}
	
	@Test
	void getResultByIdTests() throws Exception {
		when(resultService.getResultById("1")).thenReturn(Optional.of(result1));
        
        mockMvc.perform(get("/api/results/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultId").value("1"));
	}
	
	@Test
    void getResultByIdNotFoundTests() throws Exception {
		when(resultService.getResultById("1")).thenReturn(Optional.empty());
		
		mockMvc
			.perform(get("/api/results/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void getResultByStudentIdTests() throws Exception {
		when(resultService.getResultsByStudentId("kushum@gmail.com")).thenReturn(List.of(result1));
        
        mockMvc
            .perform(get("/api/results/student/kushum@gmail.com"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].studentId").value("kushum@gmail.com"));
	}
	
	@Test
    void getResultByStudentIdNotFoundTests() throws Exception {
		when(resultService.getResultsByStudentId("kushum@gmail.com")).thenReturn(List.of());
		
		mockMvc	
		    .perform(get("/api/results/student/kushum@gmail.com"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void getResultByQuizIdTests() throws Exception {
		when(resultService.getResultsByQuizId("1")).thenReturn(List.of(result1));
        
        mockMvc
            .perform(get("/api/results/quiz/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizId").value("1"));
	}
	
	@Test
    void getResultByQuizIdNotFoundTests() throws Exception {
		when(resultService.getResultsByQuizId("1")).thenReturn(List.of());
		
		mockMvc
		    .perform(get("/api/results/quiz/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test 
	void getResultByStudentIdAndQuizIdTests() throws Exception {
		when(resultService.getResultsByStudentIdAndQuizId("kushum@gmail.com", "1")).thenReturn(List.of(result1));
        
        mockMvc
            .perform(get("/api/results/student/kushum@gmail.com/quiz/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].studentId").value("kushum@gmail.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizId").value("1"));
	}
	
	@Test
    void getResultByStudentIdAndQuizIdNotFoundTests() throws Exception {
		when(resultService.getResultsByStudentIdAndQuizId("kushum@gmail.com", "1")).thenReturn(List.of());
		
        mockMvc
            .perform(get("/api/results/student/kushum@gmail.com/quiz/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void getResultByStudentIdAndCourseIdTests() throws Exception {
		when(resultService.getResultsByStudentIdAndCourseId("kushum@gmail.com", "1")).thenReturn(List.of(result1));
        
        mockMvc
            .perform(get("/api/results/student/kushum@gmail.com/course/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].studentId").value("kushum@gmail.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value("1"));
	}
	
	@Test
    void getResultByStudentIdAndCourseIdNotFoundTests() throws Exception {
		when(resultService.getResultsByStudentIdAndCourseId("kushum@gmail.com", "1")).thenReturn(List.of());
		
		mockMvc
		    .perform(get("/api/results/student/kushum@gmail.com/course/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void createResultTests() throws Exception {
		when(resultService.saveResult(result1)).thenReturn(result1);
		
		String jsonContent = objectMapper.writeValueAsString(result1);
		
		mockMvc
		    .perform(post("/api/results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isCreated());
		
		}
	
	@Test
    void updateResultTests() throws Exception {
		when(resultService.updateResult(any(Result.class))).thenReturn(true);
		
		String jsonContent = objectMapper.writeValueAsString(result1);
		
		mockMvc
		    .perform(put("/api/results/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void updateResultNotFoundTests() throws Exception {
		when(resultService.updateResult(any(Result.class))).thenReturn(false);
        
        String jsonContent = objectMapper.writeValueAsString(result1);
        
        mockMvc
            .perform(put("/api/results/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
    void deleteResultTests() throws Exception {
		when(resultService.deleteResult("1")).thenReturn(true);
		
		mockMvc
		    .perform(delete("/api/results/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
    void deleteResultNotFoundTests() throws Exception {
		when(resultService.deleteResult("1")).thenReturn(false);
		
		mockMvc
		    .perform(delete("/api/results/1"))
		    .andDo(MockMvcResultHandlers.print())
		    .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
