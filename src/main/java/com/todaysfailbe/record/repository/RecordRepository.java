package com.todaysfailbe.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.record.domain.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
	List<Record> findAllByMember(Member member);
}
