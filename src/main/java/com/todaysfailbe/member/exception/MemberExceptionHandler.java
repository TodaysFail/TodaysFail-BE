package com.todaysfailbe.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todaysfailbe.common.exception.ErrorMessage;
import com.todaysfailbe.member.controller.MemberController;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {

	@ExceptionHandler(DuplicateNameException.class)
	public ResponseEntity<ErrorMessage> DuplicateNameExceptionHandler(
			DuplicateNameException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.from(exception, exception.getMessage()));
	}
}