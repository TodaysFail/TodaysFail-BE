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

	public static String yearMonthDateConversion(LocalDate localDate) {
		StringBuilder sb = new StringBuilder();
		sb.append(localDate.getMonth().toString() + " ");
		sb.append(String.format("%02d", localDate.getMonthValue()) + " - ");
		sb.append(String.format("%02d", localDate.getDayOfMonth()) + ", ");
		sb.append(localDate.getYear());
		return sb.toString();
	}

	public static String hourMinuteSecondConversion(LocalDateTime localDateTime) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%02d", localDateTime.getHour()) + ":");
		sb.append(String.format("%02d", localDateTime.getMinute()) + ":");
		sb.append(String.format("%02d", localDateTime.getSecond()));
		return sb.toString();
	}

}
