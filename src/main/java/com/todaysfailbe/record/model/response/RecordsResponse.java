package com.todaysfailbe.record.model.response;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "실패 기록 날짜", required = true, example = "2023-02-27")
	private LocalDate date;

	@ApiModelProperty(value = "실패 기록 리스트", required = true)
	private List<RecordDto> records;

	@ApiModelProperty(value = "영수증 날짜", required = true, example = "FEBRUARY 02 - 27, 2023")
	private String receiptDate;

	@ApiModelProperty(value = "실패 기록 총 개수", required = true, example = "19")
	private Integer total;

	private RecordsResponse(LocalDate date, List<RecordDto> records, String receiptDate, Integer total) {
		this.date = date;
		this.records = records;
		this.receiptDate = receiptDate;
		this.total = total;
	}

	public static RecordsResponse from(LocalDate date, List<RecordDto> records, String receiptDate, Integer total) {
		return new RecordsResponse(date, records, receiptDate, total);
	}
}
