package com.todaysfailbe.record.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysfailbe.common.utils.DateTimeUtil;
import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.model.response.MemberDto;
import com.todaysfailbe.member.repository.MemberRepository;
import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.model.request.DeleteRecordRequest;
import com.todaysfailbe.record.model.request.RecordsRequest;
import com.todaysfailbe.record.model.response.RecordDto;
import com.todaysfailbe.record.model.response.RecordsResponse;
import com.todaysfailbe.record.repository.RecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {
	private final MemberRepository memberRepository;
	private final RecordRepository recordRepository;

	@Transactional
	public void createRecord(CreateRecordRequest request) {
		log.info("[RecordService.createRecord] 레코드 생성 요청");
		Member member = memberRepository.findByName(request.getWriter())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		recordRepository.save(Record.of(member, request));
		log.info("[RecordService.createRecord] 레코드 생성 성공");
	}

	@Transactional(readOnly = true)
	public List<RecordsResponse> getRecords(RecordsRequest request) {
		log.info("[RecordService.getRecords] 레코드 목록 조회 요청");
		Member member = memberRepository.findByName(request.getWriter())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		List<RecordsResponse> response = new ArrayList<>();

		ConcurrentMap<LocalDate, List<Record>> map = ifThereIsADateSearchByDateGetList(request, member);

		for (LocalDate date : map.keySet()) {
			List<RecordDto> records = map.get(date).stream()
					.map(record ->
							RecordDto.from(
									record,
									DateTimeUtil.hourMinuteSecondConversion(record.getCreatedAt()),
									MemberDto.from(record.getMember())
							)
					).collect(Collectors.toList());
			records.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
			response.add(RecordsResponse.from(date, records));
		}

		response.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

		log.info("[RecordService.getRecords] 레코드 목록 조회 성공");
		return response;
	}

	private ConcurrentMap<LocalDate, List<Record>> ifThereIsADateSearchByDateGetList(
			RecordsRequest request,
			Member member
	) {
		if (request.getDate() != null) {
			LocalDate date = request.getDate();
			ConcurrentMap<LocalDate, List<Record>> map = recordRepository.findAllByMemberAndCreatedAtBetween(member,
							DateTimeUtil.getStartOfDay(date), DateTimeUtil.getEndOfDay(date))
					.stream()
					.collect(Collectors.groupingByConcurrent(
									record -> {
										LocalDateTime dateTime = record.getCreatedAt();
										return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
									}
							)
					);
			return map;
		}

		ConcurrentMap<LocalDate, List<Record>> map = recordRepository.findAllByMember(member)
				.stream()
				.collect(Collectors.groupingByConcurrent(
								record -> {
									LocalDateTime dateTime = record.getCreatedAt();
									return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
								}
						)
				);
		return map;
	}

	@Transactional
	public void deleteRecord(DeleteRecordRequest request) {
		Member member = memberRepository.findByName(request.getWriter())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		Record record = recordRepository.findById(request.getRecordId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레코드입니다."));

		if (!member.equals(record.getMember())) {
			throw new IllegalArgumentException("해당 레코드를 삭제할 권한이 없습니다.");
		}

		recordRepository.delete(record);
		log.info("[RecordService.deleteRecord] 레코드 삭제 성공");
	}
}
