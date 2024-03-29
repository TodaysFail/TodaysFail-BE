package com.todaysfailbe.record.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ApiModel
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class DeleteRecordRequest {
	@NotNull(message = "실패 기록 ID는 필수입니다.")
	@ApiModelProperty(value = "실패 기록 ID", required = true, example = "1")
	private Long recordId;
}



