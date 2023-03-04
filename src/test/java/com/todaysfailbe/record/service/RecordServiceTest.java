package com.todaysfailbe.record.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.repository.MemberRepository;
import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.model.request.DeleteRecordRequest;
import com.todaysfailbe.record.model.request.RecordsRequest;
import com.todaysfailbe.record.repository.RecordRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RecordServiceTest {
	@Mock
	private MemberRepository memberRepository;

	@Mock
	private RecordRepository recordRepository;

	@InjectMocks
	private RecordService recordService;

	@Mock
	private Member mockMember;
	@Mock
	private Member mockMemberOther;

	@Mock
	private Record mockRecord;

	@Nested
	class 레코드를_생성할_때 {
		@Test
		@DisplayName("닉네임으로 회원을 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 닉네임으로_회원을_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			CreateRecordRequest request = mock(CreateRecordRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.empty());

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.createRecord(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(0)).save(recordCaptor.capture());
		}

		@Test
		@DisplayName("정상적이라면 예외를 발생시키지 않는다.")
		void 정상적이라면_예외를_발생시키지_않는() {
			// given
			CreateRecordRequest request = mock(CreateRecordRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(request.getTitle())
					.willReturn("핫케이크 태움");
			given(request.getContent())
					.willReturn("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.");
			given(request.getFeel())
					.willReturn("다음에 더 잘하면 된다");

			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.of(mockMember));

			// when, then
			assertThatCode(() -> recordService.createRecord(request))
					.doesNotThrowAnyException();
		}
	}

	@Nested
	class 레코드를_삭제할_때 {
		@Test
		@DisplayName("닉네임으로 회원을 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 닉네임으로_회원을_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.empty());

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.deleteRecord(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(0)).delete(recordCaptor.capture());
		}

		@Test
		@DisplayName("레코드가 존재하지 않는다면 예외를 발생시킨다")
		void 레코드가_존재하지_않는다면_예외를_발생시킨다() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.of(mockMember));
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.empty());

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.deleteRecord(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(0)).delete(recordCaptor.capture());
		}

		@Test
		@DisplayName("삭제 요청자와 레코드 작성자가 다르다면 예외를 발생시킨다")
		void 삭제_요청자와_레코드_작성자가_다르다면_예외를_발생시킨다() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.of(mockMember));
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.of(mockRecord));
			given(mockRecord.getMember())
					.willReturn(mockMemberOther);

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.deleteRecord(request))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage("해당 레코드를 삭제할 권한이 없습니다.");
			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(0)).delete(recordCaptor.capture());
		}

		@Test
		@DisplayName("정상적이라면 예외를 발생시키지 않는다.")
		void 정상적이라면_예외를_발생시키지_않는() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.of(mockMember));
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.of(mockRecord));
			given(mockRecord.getMember())
					.willReturn(mockMember);

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatCode(() -> recordService.deleteRecord(request))
					.doesNotThrowAnyException();
			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(1)).delete(recordCaptor.capture());
		}
	}

	@Nested
	class 레코드를_조회할_때 {
		@Test
		@DisplayName("닉네임으로 회원을 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 닉네임으로_회원을_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			RecordsRequest request = mock(RecordsRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.empty());

			// when, then
			assertThatThrownBy(() -> recordService.getRecords(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findByName(anyString());
		}

		@Test
		@DisplayName("날짜가 있다면 날짜에 해당하는 실패 기록만 조회한다")
		void 날짜가_있다면_날짜에_해당하는_실패_기록만_조회한다() {
			// given
			RecordsRequest request = mock(RecordsRequest.class);
			given(request.getWriter())
					.willReturn("도모");
			given(request.getDate())
					.willReturn(null);
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.of(mockMember));

			// when, then
			assertThatCode(() -> recordService.getRecords(request))
					.doesNotThrowAnyException();

			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(1)).findAllByMember(mockMember);
		}

		@Test
		@DisplayName("날짜가 없다면 모든 날짜에 해당하는 실패 기록만 조회한다")
		void 날짜가_없다면_모든_날짜에_해당하는_실패_기록만_조회한다() {
			// given
			RecordsRequest request = mock(RecordsRequest.class);
			LocalDate date = LocalDate.now();
			given(request.getWriter())
					.willReturn("도모");
			given(request.getDate())
					.willReturn(date);
			given(memberRepository.findByName(anyString()))
					.willReturn(Optional.of(mockMember));

			// when, then
			assertThatCode(() -> recordService.getRecords(request))
					.doesNotThrowAnyException();

			verify(memberRepository, times(1)).findByName(anyString());
			verify(recordRepository, times(1)).findAllByMemberAndCreatedAtBetween(
					mockMember,
					LocalDateTime.of(date, LocalTime.MIN),
					LocalDateTime.of(date, LocalTime.MAX)
			);
		}
	}

}