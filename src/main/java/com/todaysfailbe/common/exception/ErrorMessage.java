package com.todaysfailbe.common.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {
	private String errorSimpleName;
	private String message;
	private LocalDateTime timestamp;

	private ErrorMessage(Exception exception, String message) {
		this.errorSimpleName = exception.getClass().getSimpleName();
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public static ErrorMessage from(Exception exception, String message) {
		return new ErrorMessage(exception, message);
	}
}