package com.learn.quizService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="questions")
public class Questions {

	@Id
	private String questionId;
	private String quizId;
	private String description;
	private String type;
	private int points;
	
	// default constructor
	public Questions() {
    }
	
	// parameterized constructor
	public Questions(String questionId, String quizId, String description, String type, int points) {
		this.questionId = questionId;
        this.quizId = quizId;
        this.description = description;
        this.type = type;
        this.points = points;
	}
	
	// getters and setters
	
	public String getQuestionId() {
        return questionId;
    }
	
	public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
	
	public String getQuizId() {
        return quizId;
    }
	
	public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
	
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
        this.description = description;
    }
	
	public String getType() {
        return type;
    }
	
	public void setType(String type) {
        this.type = type;
    }
	
	public int getPoints() {
        return points;
    }
	
	public void setPoints(int points) {
        this.points = points;
    }

	
	// To string method
	@Override
    public String toString() {
        return "Questions [questionId=" + questionId + ", quizId=" + quizId + ", description=" + description + ", type="
                + type + ", points=" + points + "]";
    }
		
}
