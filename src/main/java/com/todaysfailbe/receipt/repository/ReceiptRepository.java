package com.todaysfailbe.receipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaysfailbe.receipt.domain.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
