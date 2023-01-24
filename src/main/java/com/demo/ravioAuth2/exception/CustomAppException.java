package com.demo.ravioAuth2.exception;

public class CustomAppException extends RuntimeException{
	private String message ;

	public CustomAppException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		
	}

	public CustomAppException(String message) {
		super(message);
		this.message = message;
		
	}

	public CustomAppException(Throwable cause) {
		super(cause);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
