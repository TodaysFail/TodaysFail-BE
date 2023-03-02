package com.todaysfailbe.record.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class CreateRecordRequest {
	@NotBlank(message = "작성자는 필수입니다.")
	@ApiModelProperty(value = "작성자", required = true, example = "도모")
	private String writer;

	@NotBlank(message = "제목은 필수입니다.")
	@Size(max = 17, message = "제목은 17자 이하로 입력해주세요.")
	@ApiModelProperty(value = "제목", required = true, example = "핫케이크 태움")
	private String title;

	@NotBlank(message = "내용은 필수입니다.")
	@Size(max = 300, message = "내용은 300자 이하로 입력해주세요.")
	@ApiModelProperty(value = "내용", required = true, example = "핫케이크를 태웠다. 끝.")
	private String content;

	@NotBlank(message = "느낀점은 필수입니다.")
	@Size(max = 20, message = "느낀점은 20자 이하로 입력해주세요.")
	@ApiModelProperty(value = "느낀점", required = true, example = "다음에 더 잘하면 된다")
	private String feel;
}
