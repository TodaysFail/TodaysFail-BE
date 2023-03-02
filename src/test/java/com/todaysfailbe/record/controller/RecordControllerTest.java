package com.todaysfailbe.record.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.model.request.DeleteRecordRequest;
import com.todaysfailbe.record.service.RecordService;

@WebMvcTest(RecordController.class)
class RecordControllerTest {
	@MockBean
	private RecordService recordService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Nested
	@DisplayName("레코드를 생성할 때")
	class 레코드를_생성할_때 {
		@Test
		@DisplayName("작성자를 입력하지 않았다면 예외가 발생한다.")
		void 작성자를_입력하지_않았다면_예외가_발생한다() throws Exception {
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
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("작성자는 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("제목을 입력하지 않았다면 예외가 발생한다.")
		void 제목을_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given
			CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
					.writer("도모")
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
					.writer("도모")
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
					.writer("도모")
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
					.writer("도모")
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
					.writer("도모")
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
					.writer("도모")
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
					.writer("도모")
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
		@DisplayName("작성자를 입력하지 않았다면 예외가 발생한다.")
		void 작성자를_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given

			// when, then
			mockMvc.perform(delete("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.param("recordId", "1"))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("작성자는 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("BindException"));
		}

		@Test
		@DisplayName("실패 기록 ID를 입력하지 않았다면 예외가 발생한다.")
		void 실패_기록_ID를_입력하지_않았다면_예외가_발생한다() throws Exception {
			// given
			DeleteRecordRequest deleteRecordRequest = DeleteRecordRequest.builder()
					.writer("도모")
					.build();

			// when, then
			mockMvc.perform(delete("/api/v1/record")
							.contentType(MediaType.APPLICATION_JSON)
							.param("writer", "도모"))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("실패 기록 ID는 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("BindException"));
		}

	}
}