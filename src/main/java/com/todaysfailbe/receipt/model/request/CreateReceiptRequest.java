package com.todaysfailbe.receipt.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class CreateReceiptRequest {
	@NotNull(message = "날짜는 필수입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "영수증을 생성 할 날짜", required = true, example = "2023-02-26")
	private LocalDate date;
}
