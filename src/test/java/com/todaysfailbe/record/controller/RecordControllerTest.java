package com.todaysfailbe.record.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.model.response.MemberDto;
import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.model.response.RecordDto;
import com.todaysfailbe.record.model.response.RecordsResponse;
import com.todaysfailbe.record.service.RecordService;

@WebMvcTest(RecordController.class)
@DisplayName("RecordController 테스트")
class RecordControllerTest {
	@MockBean
	private RecordService recordService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private RecordsResponse mockRecordsResponse;

	@Nested
	@DisplayName("실패기록을 생성할 때")
	class 실패기록을_생성할_때 {
		@Test
		@DisplayName("제목을 입력하지 않았다면 예외가 발생한다.")
		void 제목을_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.content("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.")
					.feel("다음에 더 잘하면 된다")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("제목은 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("제목이 17자를 초과하면 예외가 발생한다.")
		void 제목이_17자를_초과하면_예외가_발생한다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.title("일이삼사오육칠팔구십일이삼사오육칠팔")
					.content("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.")
					.feel("다음에 더 잘하면 된다")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("제목은 17자 이하로 입력해주세요."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("내용을 입력하지 않았다면 예외가 발생한다.")
		void 내용을_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.title("핫케이크 태움")
					.feel("다음에 더 잘하면 된다")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("내용은 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("내용이 300자를 초과하면 예외가 발생한다.")
		void 내용이_300자를_초과하면_예외가_발생한다() throws Exception {
			// given
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 30; i++) {
				sb.append("일이삼사오육칠팔구십");
			}
			sb.append("일");

			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.title("핫케이크 태움")
					.content(sb.toString())
					.feel("다음에 더 잘하면 된다")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("내용은 300자 이하로 입력해주세요."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("느낀점을 입력하지 않았다면 예외가 발생한다.")
		void 느낀점을_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.title("핫케이크 태움")
					.content("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("느낀점은 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("느낀점이 20자를 초과하면 예외가 발생한다.")
		void 느낀점이_20자를_초과하면_예외가_발생한다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.title("핫케이크 태움")
					.content("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.")
					.feel("일이삼사오육칠팔구십일이삼사오육칠팔구십일")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("느낀점은 20자 이하로 입력해주세요."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("입력 값이 정상이면 201 응답을 받는다.")
		void 입력_값이_정상이면_201_응답을_받는다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.title("핫케이크 태움")
					.content("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.")
					.feel("다음에 더 잘하면 된다")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createRecordRequest)))
					.andExpect(status().isCreated());
		}
	}

	@Nested
	@DisplayName("실패기록을 삭제할 때")
	class 실패기록을_삭제할_때 {
		@Test
		@DisplayName("실패 기록 ID를 입력하지 않았다면 예외가 발생한다.")
		void 실패_기록_ID를_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given
			// when, then
			mockMvc.perform(delete("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.param("writer", "도모"))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("실패 기록 ID는 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("BindException"));
		}

		@Test
		@DisplayName("입력 값이 정상이면 200 응답을 받는다.")
		void 입력_값이_정상이면_200_응답을_받는다() throws Exception {
			// given

			// when, then
			mockMvc.perform(delete("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.param("recordId", "2"))
					.andExpect(status().isOk());
		}

	}

	@Nested
	@DisplayName("실패 기록들을 조회할 때")
	class 실패_기록들을_조회할_때 {
		@Test
		@DisplayName("회원 정보를 찾을 수 없다면 예외가 발생한다.")
		void 회원_정보를_찾을_수_없다면_예외가_발생한다() throws Exception {
			// given
			given(recordService.getRecords())
					.willThrow(new IllegalArgumentException("존재하지 않는 회원입니다."));

			// when, then
			mockMvc.perform(get("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("존재하지 않는 회원입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("IllegalArgumentException"));
		}

		@Test
		@DisplayName("입력 값이 정상이면 200 응답을 받는다.")
		void 입력_값이_정상이면_200_응답을_받는다() throws Exception {
			// given
			Member mockMember = mock(Member.class);
			Record mockRecord = mock(Record.class);
			given(mockMember.getId())
					.willReturn(857L);
			given(mockMember.getName())
					.willReturn("도모");
			given(mockRecord.getId())
					.willReturn(2L);
			given(mockRecord.getTitle())
					.willReturn("핫케이크 태움");
			given(mockRecord.getContent())
					.willReturn("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.");
			given(mockRecord.getFeel())
					.willReturn("다음에 더 잘하면 된다");

			MemberDto memberDto = MemberDto.from(mockMember);
			RecordDto recordDto = RecordDto.from(mockRecord, "19:31:51", memberDto);
			LocalDate localDate = LocalDate.of(2023, 2, 27);
			RecordsResponse recordsResponse = RecordsResponse.from(localDate, List.of(recordDto),
					"FEBRUARY 02 - 27, 2023", 1);

			given(recordService.getRecords())
					.willReturn(List.of(recordsResponse));

			// when, then
			mockMvc.perform(get("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].date").value("2023-02-27"))
					.andExpect(jsonPath("$[0].records[0].id").value(2))
					.andExpect(jsonPath("$[0].records[0].title").value("핫케이크 태움"))
					.andExpect(jsonPath("$[0].records[0].content").value("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다."))
					.andExpect(jsonPath("$[0].records[0].feel").value("다음에 더 잘하면 된다"))
					.andExpect(jsonPath("$[0].records[0].createdAt").value("19:31:51"))
					.andExpect(jsonPath("$[0].records[0].member.id").value(857))
					.andExpect(jsonPath("$[0].records[0].member.name").value("도모"))
					.andExpect(jsonPath("$[0].receiptDate").value("FEBRUARY 02 - 27, 2023"))
					.andExpect(jsonPath("$[0].total").value(1))
					.andDo(print());
		}
	}

	@Nested
	@DisplayName("실패 기록을 단 건 조회할 때")
	class 실패_기록을_단_건_조회할_때 {
		@Test
		@DisplayName("실패_기록을 찾지 못한다면 예외를 발생시킨다")
		void 실패_기록을_찾지_못한다면_예외를_발생시킨다() throws Exception {
			// given
			given(recordService.getRecord(anyLong()))
					.willThrow(new IllegalArgumentException("존재하지 않는 레코드입니다."));
			// when, then
			mockMvc.perform(get("/api/v1/record/{recordId}", 2)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("존재하지 않는 레코드입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("IllegalArgumentException"));
		}

		@Test
		@DisplayName("입력 값이 정상이면 200 응답을 받는다.")
		void 입력_값이_정상이면_200_응답을_받는다() throws Exception {
			// given
			Member mockMember = mock(Member.class);
			Record mockRecord = mock(Record.class);
			given(mockMember.getId())
					.willReturn(857L);
			given(mockMember.getName())
					.willReturn("도모");
			given(mockRecord.getId())
					.willReturn(2L);
			given(mockRecord.getTitle())
					.willReturn("핫케이크 태움");
			given(mockRecord.getContent())
					.willReturn("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다.");
			given(mockRecord.getFeel())
					.willReturn("다음에 더 잘하면 된다");

			MemberDto memberDto = MemberDto.from(mockMember);
			RecordDto recordDto = RecordDto.from(mockRecord, "19:31:51", memberDto);
			given(recordService.getRecord(anyLong()))
					.willReturn(recordDto);

			// when, then
			mockMvc.perform(get("/api/v1/record/{recordId}", 2)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").value(2))
					.andExpect(jsonPath("$.title").value("핫케이크 태움"))
					.andExpect(jsonPath("$.content").value("분명히 가스불을 약으로 했는데 "
							+ "어느 순간 분명히 가스불을 약으로 했는데 어느 순간 재가 됐다."))
					.andExpect(jsonPath("$.feel").value("다음에 더 잘하면 된다"))
					.andExpect(jsonPath("$.createdAt").value("19:31:51"))
					.andExpect(jsonPath("$.member.id").value(857))
					.andExpect(jsonPath("$.member.name").value("도모"))
					.andDo(print());
		}
	}
}