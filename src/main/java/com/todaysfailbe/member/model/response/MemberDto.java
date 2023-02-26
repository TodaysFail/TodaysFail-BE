package com.todaysfailbe.member.model.response;

import com.todaysfailbe.member.domain.Member;

import io.swagger.annotations.ApiModel;
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
	private Long id;

	private String name;

	private MemberDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static MemberDto from(Member member) {
		return new MemberDto(member.getId(), member.getName());
	}
}
