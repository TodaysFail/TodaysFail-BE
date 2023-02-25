package com.todaysfailbe.receipt.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.util.Assert;

import com.todaysfailbe.common.domain.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Receipt extends BaseEntity {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "RECEIPT_ID")
	private Long id;

	@ElementCollection
	private List<Long> recordIds;

	public Receipt(List<Long> recordIds) {
		Assert.notNull(recordIds, "실패 기록 ID 리스트는 필수입니다.");
		this.recordIds = recordIds;
	}
}
