package com.todaysfailbe.common.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@NotNull
	@CreatedDate
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@NotNull
	@LastModifiedDate
	@Column(name = "MODIFIED_AT")
	private LocalDateTime modifiedAt;

	@Column(name = "DELETED_AT")
	private LocalDateTime deletedAt;
}
