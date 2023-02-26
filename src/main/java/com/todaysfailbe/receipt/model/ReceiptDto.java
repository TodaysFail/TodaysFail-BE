package com.todaysfailbe.receipt.model;

import com.todaysfailbe.common.utils.DateTimeUtil;
import com.todaysfailbe.record.domain.Record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ApiModel
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {
	@ApiParam(value = "실패 기록 ID", required = true, example = "1")
	private Long id;

	@ApiParam(value = "실패 기록 제목", required = true, example = "핫케이크 태움")
	private String title;

	@ApiParam(value = "실패 기록 내용", required = true, example = "핫케이크를 타다가 불이 났다.")
	private String content;

	@ApiParam(value = "실패 기록 느낀점", required = true, example = "다음에 더 잘하면 된다")
	private String feel;

	@ApiParam(value = "실패 기록 시:분:초", required = true, example = "19:31:51")
	private String createdAt;

	public static ReceiptDto from(Record record) {
		return new ReceiptDto(
				record.getId(),
				record.getTitle(),
				record.getContent(),
				record.getFeel(),
				DateTimeUtil.hourMinuteSecondConversion(record.getCreatedAt())
		);
	}
}
