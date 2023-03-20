package com.todaysfailbe.record.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.todaysfailbe.common.utils.SessionUtil;
import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.repository.MemberRepository;
import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.model.request.DeleteRecordRequest;
import com.todaysfailbe.record.model.response.RecordDto;
import com.todaysfailbe.record.repository.RecordRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("RecordService 테스트")
class RecordServiceTest {
	@Mock
	private MemberRepository memberRepository;

	@Mock
	private RecordRepository recordRepository;

	@Mock
	private SessionUtil sessionUtil;

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
		@DisplayName("회원을 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 회원을_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			CreateRecordRequest request = mock(CreateRecordRequest.class);
			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.empty());

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.createRecord(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(0)).save(recordCaptor.capture());
		}

		@Test
		@DisplayName("정상적이라면 예외를 발생시키지 않는다.")
		void 정상적이라면_예외를_발생시키지_않는() {
			// given
			CreateRecordRequest request = mock(CreateRecordRequest.class);
			given(request.getTitle())
					.willReturn("핫케이크 태움");
			given(request.getContent())
					.willReturn("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.");
			given(request.getFeel())
					.willReturn("다음에 더 잘하면 된다");

			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.of(mockMember));

			// when, then
			assertThatCode(() -> recordService.createRecord(request))
					.doesNotThrowAnyException();
		}
	}

	@Nested
	class 레코드를_삭제할_때 {
		@Test
		@DisplayName("회원을 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 회원을_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.empty());

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.deleteRecord(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(0)).delete(recordCaptor.capture());
		}

		@Test
		@DisplayName("레코드가 존재하지 않는다면 예외를 발생시킨다")
		void 레코드가_존재하지_않는다면_예외를_발생시킨다() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.of(mockMember));
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.empty());

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatThrownBy(() -> recordService.deleteRecord(request))
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(0)).delete(recordCaptor.capture());
		}

		@Test
		@DisplayName("삭제 요청자와 레코드 작성자가 다르다면 예외를 발생시킨다")
		void 삭제_요청자와_레코드_작성자가_다르다면_예외를_발생시킨다() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(memberRepository.findById(anyLong()))
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
			verify(memberRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(0)).delete(recordCaptor.capture());
		}

		@Test
		@DisplayName("정상적이라면 예외를 발생시키지 않는다.")
		void 정상적이라면_예외를_발생시키지_않는() {
			// given
			DeleteRecordRequest request = mock(DeleteRecordRequest.class);
			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.of(mockMember));
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.of(mockRecord));
			given(mockRecord.getMember())
					.willReturn(mockMember);

			ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);

			// when, then
			assertThatCode(() -> recordService.deleteRecord(request))
					.doesNotThrowAnyException();
			verify(memberRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(1)).delete(recordCaptor.capture());
		}
	}

	@Nested
	class 레코드를_여러_건_조회할_때 {
		@Test
		@DisplayName("회원을 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 회원을_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.empty());

			// when, then
			assertThatThrownBy(() -> recordService.getRecords())
					.isInstanceOf(IllegalArgumentException.class);
			verify(memberRepository, times(1)).findById(anyLong());
		}

		@Test
		@DisplayName("모든 날짜에 해당하는 실패 기록만 조회한다")
		void 모든_날짜에_해당하는_건실패_기록만_조회한다() {
			// given
			LocalDate date = LocalDate.now();
			given(memberRepository.findById(anyLong()))
					.willReturn(Optional.of(mockMember));

			// when, then
			assertThatCode(() -> recordService.getRecords())
					.doesNotThrowAnyException();

			verify(memberRepository, times(1)).findById(anyLong());
			verify(recordRepository, times(1)).findAllByMember(mockMember);
		}
	}

	@Nested
	class 레코드를_단_건_조회할_때 {
		@Test
		@DisplayName("레코드 조회 시 찾을 수 없다면 예외를 발생시킨다")
		void 레코드_조회_시_찾을_수_없다면_예외를_발생시킨다() {
			// given
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.empty());
			// when, then
			assertThatThrownBy(() -> recordService.getRecord(anyLong()))
					.isInstanceOf(IllegalArgumentException.class);
			verify(recordRepository, times(1)).findById(anyLong());
		}

		@Test
		@DisplayName("레코드 조회 시 찾을 수 있다면 예외를 발생시키지 않는다")
		void 레코드_조회_시_찾을_수_있다면_예외를_발생시키지_않는다() {
			// given
			LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 1, 18, 0, 0);
			given(recordRepository.findById(anyLong()))
					.willReturn(Optional.of(mockRecord));

			given(mockRecord.getMember())
					.willReturn(mockMember);
			given(mockRecord.getCreatedAt())
					.willReturn(localDateTime);

			given(mockMember.getId())
					.willReturn(18234L);
			given(mockMember.getName())
					.willReturn("도모");

			given(mockRecord.getId())
					.willReturn(3142L);
			given(mockRecord.getTitle())
					.willReturn("핫케이크 태움");
			given(mockRecord.getContent())
					.willReturn("핫케이크를 타다가 불이 났다.");
			given(mockRecord.getFeel())
					.willReturn("다음에 더 잘하면 된다");

			// when, then
			assertThatCode(() -> recordService.getRecord(anyLong()))
					.doesNotThrowAnyException();

			RecordDto recordDto = recordService.getRecord(anyLong());

			assertThat(recordDto.getMember().getId()).isEqualTo(18234L);
			assertThat(recordDto.getMember().getName()).isEqualTo("도모");

			assertThat(recordDto.getId()).isEqualTo(3142L);
			assertThat(recordDto.getTitle()).isEqualTo("핫케이크 태움");
			assertThat(recordDto.getContent()).isEqualTo("핫케이크를 타다가 불이 났다.");
			assertThat(recordDto.getFeel()).isEqualTo("다음에 더 잘하면 된다");
			assertThat(recordDto.getCreatedAt()).isEqualTo("18:00:00");
		}
	}

}