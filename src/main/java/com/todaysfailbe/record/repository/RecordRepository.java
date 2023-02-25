package com.todaysfailbe.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaysfailbe.record.domain.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
