package com.todaysfailbe.member.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {
	@NotBlank(message = "이름은 필수입니다.")
	@Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
	private String name;
}
