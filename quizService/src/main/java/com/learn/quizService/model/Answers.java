package com.learn.quizService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="answers")
public class Answers {
	
	@Id
	private String answerId;
	private String questionId;
	private String answerText;
	private boolean isCorrect;
	
	// default constructor
	public Answers() {
    }
	
	// parameterized constructor
	public Answers(String answerId, String questionId, String answerText, boolean isCorrect) {
		this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
	}
	
	// getters and setters
	
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public boolean getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	// To string method
	@Override
	public String toString() {
		return "Answers [answerId=" + answerId + ", questionId=" + ", answerText=" + answerText
				+ ", isCorrect=" + isCorrect + "]";
	}
	
	
    
}
