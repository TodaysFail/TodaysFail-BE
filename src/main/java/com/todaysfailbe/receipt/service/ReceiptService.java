package com.todaysfailbe.receipt.service;

import static com.todaysfailbe.common.utils.DateTimeUtil.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.repository.MemberRepository;
import com.todaysfailbe.receipt.domain.Receipt;
import com.todaysfailbe.receipt.model.ReceiptDto;
import com.todaysfailbe.receipt.model.request.CreateReceiptRequest;
import com.todaysfailbe.receipt.model.response.ReceiptResponse;
import com.todaysfailbe.receipt.repository.ReceiptRepository;
import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.repository.RecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptService {
	private final ReceiptRepository receiptRepository;
	private final RecordRepository recordRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public String createReceipt(CreateReceiptRequest request) {
		log.info("[ReceiptService.createReceipt] 영수증 등록 요청: {}", request);
		Member member = memberRepository.findByName(request.getWriter())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		LocalDateTime start = getStartOfDay(request.getDate());
		LocalDateTime end = getEndOfDay(request.getDate());

		List<Long> list = recordRepository.findAllByMemberAndCreatedAtBetween(member, start, end)
				.stream().map(Record::getId)
				.collect(Collectors.toList());

		if (list.size() == 0) {
			log.info("[ReceiptService.createReceipt] 해당 날짜에 해당하는 실패 기록이 없습니다: {}", request);
			throw new IllegalArgumentException("해당 날짜에 해당하는 실패 기록이 없습니다.");
		}

		Receipt receipt = receiptRepository.save(Receipt.from(list));
		log.info("[ReceiptService.createReceipt] 영수증 등록 완료: {}", request);
		return receipt.getId().toString();
	}

	public ReceiptResponse getReceipt(String receiptId) {
		log.info("[ReceiptService.getReceipt] 영수증 조회 요청: {}", receiptId);
		Receipt receipt = receiptRepository.findById(UUID.fromString(receiptId))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영수증입니다."));

		List<ReceiptDto> receiptDtoList = recordRepository.findAllById(receipt.getRecordIds())
				.stream()
				.map(ReceiptDto::from)
				.collect(Collectors.toList());

		ReceiptResponse response = ReceiptResponse.from(receiptDtoList,
				yearMonthDateConversion(receipt.getCreatedAt()));
		log.info("[ReceiptService.getReceipt] 영수증 조회 완료: {}", response);
		return response;
	}
}
