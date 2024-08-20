package com.learn.quizService.exception;

public class EmptyRequestBodyException extends RuntimeException {
	
	public EmptyRequestBodyException(String message) {
		super(message);
	}

}
