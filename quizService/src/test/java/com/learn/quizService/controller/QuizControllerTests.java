package com.learn.quizService.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
import com.learn.quizService.model.AttemptQuizRequest;
import com.learn.quizService.model.Quiz;
import com.learn.quizService.model.Result;
import com.learn.quizService.service.QuizService;

@WebMvcTest(QuizController.class)
public class QuizControllerTests {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private QuizService quizService;
	
	@Autowired
	private QuizController quizController;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private Quiz quiz1;
	private Quiz quiz2;
	
    @BeforeEach
    void setUp(){
    	
        quiz1 = new Quiz("1", "1", "Java Quiz", LocalDateTime.now(), 10, "00:15:00");
        MockitoAnnotations.openMocks(this);
        
        quiz2 = new Quiz("2", "2", "Python Quiz", LocalDateTime.now(), 15, "00:20:00");
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();    
    }
     
    @Test
    void getAllQuizzesTests() throws Exception {
    	List<Quiz> quizzes = List.of(quiz1, quiz2);
    	
    	when(quizService.getAllQuizzes()).thenReturn(quizzes);
    	
    	mockMvc
    	    .perform(get("/api/quizzes"))
    	    .andDo(MockMvcResultHandlers.print())
    	    .andExpect(MockMvcResultMatchers.status().isOk())
    	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizId").value("1"))
    	    .andExpect(MockMvcResultMatchers.status().isOk())
    	    .andExpect(MockMvcResultMatchers.jsonPath("$[1].quizId").value("2"));
    }
    
    @Test
    void getQuizByIdTests() throws Exception {
    	when(quizService.getQuizById("1")).thenReturn(quiz1);
        
        mockMvc
            .perform(get("/api/quizzes/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.quizId").value("1"));
            
    }
    
    @Test
    void getQuizByIdNotFoundTests() throws Exception {
        when(quizService.getQuizById("1")).thenReturn(null);

        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    void getQuizzesByCourseIdTests() throws Exception {
    	when(quizService.getQuizzesByCourseId("1")).thenReturn(List.of(quiz1));
    	
    	mockMvc
    		.perform(get("/api/quizzes/course/1"))
    		.andDo(MockMvcResultHandlers.print())
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value("1"));
    }
    
    @Test
    void getQuizzesByCourseIdNotFoundTests() throws Exception {
    	when(quizService.getQuizzesByCourseId("1")).thenReturn(List.of());
        
        mockMvc
            .perform(get("/api/quizzes/course/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    void getQuizByQuizTitleTests() throws Exception {
    	when(quizService.getQuizByQuizTitle("Java Quiz")).thenReturn(List.of(quiz1));
        
        mockMvc
            .perform(get("/api/quizzes/title/Java Quiz"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizTitle").value("Java Quiz"));
    }
    
    @Test
    void getQuizByQuizTitleNotFoundTests() throws Exception {
    	when(quizService.getQuizByQuizTitle("Java Quiz")).thenReturn(List.of());
        
        mockMvc
            .perform(get("/api/quizzes/title/Java Quiz"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    void createQuizTests() throws Exception {
    	when(quizService.createQuiz(quiz1)).thenReturn(quiz1);
    	
    	mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quiz1)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    void deleteQuizTest() throws Exception {
    	when(quizService.deleteQuiz("1")).thenReturn(true);
    	
    	mockMvc
    	    .perform(delete("/api/quizzes/1"))
    	    .andDo(MockMvcResultHandlers.print())
    	    .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    
    @Test
    void deleteQuizNotFoundTest() throws Exception {
    	when(quizService.deleteQuiz("1")).thenReturn(false);
        
        mockMvc
            .perform(delete("/api/quizzes/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    void updateQuizTests() throws Exception {
    	when(quizService.updateQuiz(any(Quiz.class))).thenReturn(true);
        
        mockMvc.perform(put("/api/quizzes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quiz1)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    void updateQuizNotFoundTests() throws Exception {
    	when(quizService.updateQuiz(any(Quiz.class))).thenReturn(false);
        
        mockMvc
            .perform(put("/api/quizzes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(quiz1)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    void deleteQuizByCourseIdTest() throws Exception {
    	mockMvc
    	    .perform(delete("/api/quizzes/course/1"))
    	    .andDo(MockMvcResultHandlers.print())
    	    .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    
    @Test
    void attemptQuizTest() throws Exception {
    	Map<String, String> answers = Map.of("Q1", "A", "Q2", "B");

        AttemptQuizRequest request = new AttemptQuizRequest();
        request.setAnswers(answers);
        request.setQuizId("1");

        Result result = new Result("1", "kushum@gmail.com", "1", "1", LocalDateTime.now(), 30, 40);
        when(quizService.attemptQuiz(answers, "1", "kushum@gmail.com")).thenReturn(result);

        mockMvc.perform(post("/api/quizzes/attempt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("email", "kushum@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
