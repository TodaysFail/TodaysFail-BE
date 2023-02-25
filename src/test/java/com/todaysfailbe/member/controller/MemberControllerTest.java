package com.todaysfailbe.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.todaysfailbe.member.model.request.CreateMemberRequest;
import com.todaysfailbe.member.service.MemberService;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
	@MockBean
	private MemberService memberService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private Member mockMember;

	@Nested
	@DisplayName("회원을 생성할 때")
	class 회원을_생성할_때 {
		@Test
		@DisplayName("name이 없으면 예외가 발생한다.")
		void name이_없으면_예외가_발생한다() throws Exception {
			//given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("이름은 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("name이 10자를 초과하면 예외가 발생한다.")
		void name이_10자를_초과하면_예외가_발생한다() throws Exception {
			//given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("12345678901")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("이름은 10자 이하로 입력해주세요."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("입력 값이 정상적이라면 회원을 생성한다.")
		void 입력_값이_정상적이라면_회원을_생성한다() throws Exception {
			// given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("주니")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isCreated());
		}
	}
}