package com.todaysfailbe.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import com.todaysfailbe.common.domain.BaseEntity;
import com.todaysfailbe.member.model.request.CreateMemberRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE Member SET Member.DELETED_AT = CURRENT_TIMESTAMP WHERE Member.MEMBER_ID = ?")
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PASSWORD")
	private String password;

	private Member(String name, String password) {
		Assert.hasText(name, "이름은 필수입니다.");
		Assert.hasText(password, "비밀번호는 필수입니다.");
		this.name = name;
		this.password = password;
	}

	public static Member from(CreateMemberRequest request) {
		return new Member(request.getName(), request.getPassword());
	}
}
