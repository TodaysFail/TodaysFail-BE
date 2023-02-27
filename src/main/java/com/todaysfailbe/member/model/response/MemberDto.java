package com.todaysfailbe.member.model.response;

import com.todaysfailbe.member.domain.Member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ApiModel
@ToString
@NoArgsConstructor
public class MemberDto {
	@ApiModelProperty(value = "실패 기록한 회원 정보", required = true, example = "1")
	private Long id;

	@ApiModelProperty(value = "실패 기록한 회원 정보", required = true, example = "도모")
	private String name;

	private MemberDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static MemberDto from(Member member) {
		return new MemberDto(member.getId(), member.getName());
	}
}
