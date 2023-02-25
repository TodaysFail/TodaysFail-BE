package com.todaysfailbe.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.Assert;

import com.todaysfailbe.common.domain.BaseEntity;
import com.todaysfailbe.member.model.request.CreateMemberRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	private Member(String name) {
		Assert.hasText(name, "이름은 필수입니다.");
		this.name = name;
	}

	public static Member from(CreateMemberRequest request) {
		return new Member(request.getName());
	}
}
