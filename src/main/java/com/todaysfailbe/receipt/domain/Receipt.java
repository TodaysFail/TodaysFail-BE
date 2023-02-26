package com.todaysfailbe.receipt.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import com.todaysfailbe.common.domain.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE Receipt SET Receipt.DELETED_AT = CURRENT_TIMESTAMP WHERE Receipt.RECEIPT_ID = ?")
public class Receipt extends BaseEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "RECEIPT_ID")
	private UUID id;

	@ElementCollection
	private List<Long> recordIds;

	private Receipt(List<Long> recordIds) {
		Assert.notNull(recordIds, "실패 기록 ID 리스트는 필수입니다.");
		this.recordIds = recordIds;
	}

	public static Receipt from(List<Long> recordIds) {
		return new Receipt(recordIds);
	}
}
