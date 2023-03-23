package com.todaysfailbe.receipt.domain;

import java.time.LocalDate;
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

	private String writerName;

	@ElementCollection
	private List<Long> recordIds;

	private LocalDate receiptDate;

	private Receipt(String writerName, List<Long> recordIds, LocalDate receiptDate) {
		Assert.notNull(writerName, "작성자 이름은 필수입니다.");
		Assert.notNull(recordIds, "실패 기록 ID 리스트는 필수입니다.");
		Assert.notNull(receiptDate, "영수증 발급일은 필수입니다.");
		this.writerName = writerName;
		this.recordIds = recordIds;
		this.receiptDate = receiptDate;
	}

	public static Receipt from(String writerName, List<Long> recordIds, LocalDate receiptDate) {
		return new Receipt(writerName, recordIds, receiptDate);
	}
}
