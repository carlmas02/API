package com.learn.quizService.model;

import java.util.Map;

public class AttemptQuizRequest {
	
	// Map for questions and answers
	private Map<String, String> answers;
    private String quizId;
    
    // Constructor
    public AttemptQuizRequest() {
    	
    }
    
    // Parameterized constructor
    
    public AttemptQuizRequest(Map<String, String> answers, String quizId) {
        this.answers = answers;
        this.quizId = quizId;
    }

    // Getters and setters
    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

}
