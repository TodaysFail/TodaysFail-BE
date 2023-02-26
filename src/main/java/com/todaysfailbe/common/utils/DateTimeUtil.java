package com.todaysfailbe.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
	public static LocalDateTime getStartOfDay(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MIN);
	}

	public static LocalDateTime getEndOfDay(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MAX);
	}
}
