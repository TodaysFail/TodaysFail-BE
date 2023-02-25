package com.todaysfailbe.record.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

import com.todaysfailbe.common.domain.BaseEntity;
import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.record.model.request.CreateRecordRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Record extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECORD_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "FEEL")
	private String feel;

	private Record(Member member, String title, String content, String feel) {
		Assert.notNull(member, "회원은 필수입니다.");
		Assert.notNull(title, "제목은 필수입니다.");
		Assert.notNull(content, "내용은 필수입니다.");
		Assert.notNull(feel, "느낀점은 필수입니다.");
		this.member = member;
		this.title = title;
		this.content = content;
		this.feel = feel;
	}

	public static Record of(Member member, CreateRecordRequest request) {
		return new Record(
				member,
				request.getTitle(),
				request.getContent(),
				request.getFeel()
		);
	}
}
