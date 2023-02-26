package com.todaysfailbe.record.model.response;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ApiModel
@ToString
@NoArgsConstructor
public class RecordsResponse {
	private LocalDate date;

	private List<RecordDto> records;

	private RecordsResponse(LocalDate date, List<RecordDto> records) {
		this.date = date;
		this.records = records;
	}

	public static RecordsResponse from(LocalDate date, List<RecordDto> records) {
		return new RecordsResponse(date, records);
	}
}
