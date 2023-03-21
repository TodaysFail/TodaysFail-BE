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
public class UpdateRecordRequest {
	@NotNull(message = "실패 기록 ID는 필수입니다.")
	@ApiModelProperty(value = "실패 기록 ID", required = true, example = "1")
	private Long recordId;

	@NotNull(message = "제목은 필수입니다.")
	@ApiModelProperty(value = "변경 할 제목", required = true, example = "핫케이크 태움")
	private String title;

	@NotNull(message = "내용은 필수입니다.")
	@ApiModelProperty(value = "변경 할 내용", required = true, example = "오늘 핫케이크 만들다가 실패했다.")
	private String content;

	@NotNull(message = "느낀점은 필수입니다.")
	@ApiModelProperty(value = "변경 할 느낀점", required = true, example = "다음에 더 잘하면 된다")
	private String feel;
}



