package com.springboot.main.exception;

public class InvalidIdException extends Exception{

	
	
	private static final long serialVersionUID = 2886693966507369162L;
	private String message;
	public InvalidIdException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
