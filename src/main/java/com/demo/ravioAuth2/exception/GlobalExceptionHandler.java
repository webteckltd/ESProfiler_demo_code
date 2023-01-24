package com.demo.ravioAuth2.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Value("${generic.error}")
	private String generiErrorMessage;
	
	 @ExceptionHandler(CustomAppException.class)
	 public ResponseEntity<String> applicationException(CustomAppException ex) {
			 return new ResponseEntity<String>(generiErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR); 
		 }
    

}
