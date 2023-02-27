package com.todaysfailbe.record.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ApiModel
@ToString
@AllArgsConstructor
public class RecordsRequest {
	@NotBlank(message = "작성자는 필수입니다.")
	@ApiModelProperty(value = "작성자", required = true, example = "주니")
	private String writer;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "조회 할 날짜", example = "2023-02-26")
	private LocalDate date;
}
