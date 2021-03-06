package com.hb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestValidException {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handlerException(MethodArgumentNotValidException ex){
		System.out.println("exception 호출");
		String errorCode = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorCode);
	}
	
}