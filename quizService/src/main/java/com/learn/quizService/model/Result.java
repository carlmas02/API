package com.learn.quizService.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="results")
public class Result {

	@Id
	private String resultId;
	private String studentId;
	private String courseId;
	private String quizId;
	private LocalDateTime attemptedOn;
	private int marksScored;
	private int totalMarks;
	
	// Constructors
	
	public Result() {
    }
	
	public Result(String resultId, String studentId, String courseId, String quizId, LocalDateTime attemptedOn, int marksScored, int totalMarks) {
		this.resultId = resultId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.quizId = quizId;
        this.attemptedOn = attemptedOn;
        this.marksScored = marksScored;
        this.totalMarks = totalMarks;
	}
	
	// getters and setters
	
	public String getResultId() {
        return resultId;
    }
	
	public void setResultId(String resultId) {
        this.resultId = resultId;
    }
	
	public String getStudentId() {
        return studentId;
    }
	
	public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
	
	public String getCourseId() {
        return courseId;
    }
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public LocalDateTime getAttemptedOn() {
		return attemptedOn;
	}

	public void setAttemptedOn(LocalDateTime attemptedOn) {
		this.attemptedOn = attemptedOn;
	}
	
	public int getMarksScored() {
        return marksScored;
    }
	
	public void setMarksScored(int marksScored) {
        this.marksScored = marksScored;
    }

	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}

	// To string method
	
	@Override
	public String toString() {
		return "Result [resultId=" + resultId + ", studentId=" + studentId + ", courseId=" + courseId + ", quizId="
				+ quizId + ", attemptedOn=" + attemptedOn + ", marksScored=" + marksScored + ", totalMarks="
				+ totalMarks + "]";
	}	
	
}
