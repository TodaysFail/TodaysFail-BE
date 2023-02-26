package com.todaysfailbe.record.model.response;

import com.todaysfailbe.member.model.response.MemberDto;
import com.todaysfailbe.record.domain.Record;

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
public class RecordDto {
	@ApiModelProperty(value = "실패 기록 ID", required = true, example = "1")
	private Long id;

	@ApiModelProperty(value = "실패 기록 제목", required = true, example = "핫케이크 태움")
	private String title;

	@ApiModelProperty(value = "실패 기록 내용", required = true, example = "핫케이크를 타다가 불이 났다.")
	private String content;

	@ApiModelProperty(value = "실패 기록 느낀점", required = true, example = "다음에 더 잘하면 된다")
	private String feel;

	@ApiModelProperty(value = "실패 기록 시:분:초", required = true, example = "19:31:51")
	private String createdAt;

	private MemberDto member;

	public RecordDto(Long id, String title, String content, String feel, String createdAt, MemberDto member) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.feel = feel;
		this.createdAt = createdAt;
		this.member = member;
	}

	public static RecordDto from(Record record, String createdAt, MemberDto memberDto) {
		return new RecordDto(
				record.getId(),
				record.getTitle(),
				record.getContent(),
				record.getFeel(),
				createdAt,
				memberDto
		);
	}
}
