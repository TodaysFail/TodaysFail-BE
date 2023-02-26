package com.todaysfailbe.record.model.request;

import javax.validation.constraints.NotBlank;

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
}
