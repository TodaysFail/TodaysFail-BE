package com.todaysfailbe.record.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class CreateRecordRequest {
	@NotBlank(message = "작성자을 필수입니다.")
	@ApiParam(value = "작성자", required = true)
	private String writer;

	@NotBlank(message = "제목은 필수입니다.")
	@Size(max = 17, message = "제목은 17자 이하로 입력해주세요.")
	@ApiParam(value = "제목", required = true)
	private String title;

	@NotBlank(message = "내용은 필수입니다.")
	@Size(max = 300, message = "내용은 300자 이하로 입력해주세요.")
	@ApiParam(value = "내용", required = true)
	private String content;

	@NotBlank(message = "느낀점은 필수입니다.")
	@Size(max = 20, message = "느낀점은 20자 이하로 입력해주세요.")
	@ApiParam(value = "느낀점", required = true)
	private String feel;
}
