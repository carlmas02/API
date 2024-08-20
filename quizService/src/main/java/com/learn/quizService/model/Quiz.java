package com.learn.quizService.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="quizzes")
public class Quiz {

	@Id
	private String quizId;
	private String courseId;
	private String quizTitle;
	private LocalDateTime createdAt;
	private int totalQuestions;
	private String duration;
	
	// Constructors
	
	public Quiz() {
    }
	
	public Quiz(String quizId, String courseId, String quizTitle, LocalDateTime createdAt, int totalQuestions, String duration) {
		this.quizId = quizId;
		this.courseId = courseId;
		this.quizTitle = quizTitle;
		this.createdAt = createdAt;
		this.totalQuestions = totalQuestions;
		this.duration = duration;
	}
	
	// getters and setters
	
	public String getQuizId() {
		return quizId;
	}
	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getQuizTitle() {
		return quizTitle;
	}
	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
	// To string method
	
	@Override
	public String toString() {
		return "Quiz [quizId=" + quizId + ", courseId=" + courseId + ", quizTitle=" + quizTitle + ", createdAt="
				+ createdAt + ", totalQuestions=" + totalQuestions + ", duration=" + duration + "]";
	}
	
}
