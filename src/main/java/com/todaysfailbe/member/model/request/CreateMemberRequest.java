package com.todaysfailbe.member.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ApiModel
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {
	@NotBlank(message = "닉네임은 필수입니다.")
	@Size(max = 10, message = "닉네임은 10자 이하로 입력해주세요.")
	@ApiModelProperty(value = "닉네임", required = true, example = "도모")
	private String name;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\w\\W]{8,}$", message = "비밀번호는 8자 이상, 영문, 숫자를 포함해야 합니다.")
	@Size(max = 16, message = "비밀번호는 16자 이하로 입력해주세요.")
	@ApiModelProperty(value = "비밀번호", required = true, example = "asdf1234 (8자 이상, 영문, 숫자 포함)")
	private String password;
}
