package com.spring.jpa.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(StudentAlreadyExistException.class)
	public ResponseEntity<String> HandleStudentAlreadyExistException(StudentAlreadyExistException exception) {
		return new ResponseEntity<String>("Student Data Already Exist, Please Provide Unique!", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResoucreNotFoundException.class)
	public ResponseEntity<String> handleResoucreNotFoundException(ResoucreNotFoundException ex) {
		return new ResponseEntity<String>("Student With Give Is Id Not Found On Server!", HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return new ResponseEntity<Object>("Please Change HTTP Method Type", HttpStatus.NOT_FOUND);
	}

}
