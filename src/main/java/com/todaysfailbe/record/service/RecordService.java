package com.todaysfailbe.record.service;

import org.springframework.stereotype.Service;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.repository.MemberRepository;
import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.repository.RecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {
	private final MemberRepository memberRepository;
	private final RecordRepository recordRepository;

	public void createRecord(CreateRecordRequest request) {
		log.info("[RecordService.createRecord] 레코드 생성 요청");
		Member member = memberRepository.findByName(request.getWriter())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		recordRepository.save(Record.of(member, request));
		log.info("[RecordService.createRecord] 레코드 생성 성공");
	}
}
