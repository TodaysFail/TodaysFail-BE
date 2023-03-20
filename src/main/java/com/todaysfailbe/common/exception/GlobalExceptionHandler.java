package com.todaysfailbe.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.from(exception, message));
	}

	@ExceptionHandler(NotFoundUserInfoInSessionException.class)
	public ResponseEntity<ErrorMessage> handleNotFoundUserInfoInSessionException(
			NotFoundUserInfoInSessionException exception) {
		String message = exception.getMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.from(exception, message));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> handleIllegalArgumentException(
			IllegalArgumentException exception) {
		String message = exception.getMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.from(exception, message));
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorMessage> handleBindException(
			BindException exception) {
		String message = exception.getBindingResult()
				.getFieldError().getDefaultMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.from(exception, message));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(
			Exception exception) {
		String message = exception.getMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.from(exception, message));
	}
}
