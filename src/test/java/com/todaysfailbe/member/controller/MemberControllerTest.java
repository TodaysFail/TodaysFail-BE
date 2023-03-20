package com.todaysfailbe.member.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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
import com.todaysfailbe.member.model.response.MemberDto;
import com.todaysfailbe.member.service.MemberService;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 테스트")
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
		@DisplayName("닉네임이 없으면 예외가 발생한다.")
		void 닉네임이_없으면_예외가_발생한다() throws Exception {
			//given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.password("abcd1234")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("닉네임은 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("닉네임이 10자를 초과하면 예외가 발생한다.")
		void 닉네임이_10자를_초과하면_예외가_발생한다() throws Exception {
			//given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("12345678901")
					.password("abcd1234")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("닉네임은 10자 이하로 입력해주세요."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("비밀번호가 없으면 예외가 발생한다.")
		void 비밀번호가_없으면_예외가_발생한다() throws Exception {
			// given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("도모")
					.build();
			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("비밀번호는 필수입니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("비밀번호가 8자보다 작으면 예외가 발생한다.")
		void 비밀번호가_8자보다_작으면_예외가_발생한다() throws Exception {
			// given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("도모")
					.password("1234567")
					.build();
			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("비밀번호는 8자 이상, 영문, 숫자를 포함해야 합니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("비밀번호가 영문만 포함하면 예외가 발생한다.")
		void 비밀번호가_영문만_포함하면_예외가_발생한다() throws Exception {
			// given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("도모")
					.password("abcdefgh")
					.build();
			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("비밀번호는 8자 이상, 영문, 숫자를 포함해야 합니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("비밀번호가 숫자만 포함하면 예외가 발생한다.")
		void 비밀번호가_숫자만_포함하면_예외가_발생한다() throws Exception {
			// given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("도모")
					.password("12345678")
					.build();
			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.message").value("비밀번호는 8자 이상, 영문, 숫자를 포함해야 합니다."))
					.andExpect(jsonPath("$.errorSimpleName").value("MethodArgumentNotValidException"));
		}

		@Test
		@DisplayName("입력 값이 정상적이라면 회원을 생성한다.")
		void 입력_값이_정상적이라면_회원을_생성한다() throws Exception {
			// given
			CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
					.name("주니")
					.password("abcd1234")
					.build();

			// when, then
			mockMvc.perform(post("/api/v1/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(createMemberRequest)))
					.andExpect(status().isCreated());
		}
	}

	@Nested
	@DisplayName("닉네임 중복체크할 때")
	class 닉네임_중복체크할_때 {
		@Test
		@DisplayName("닉네임이 중복된다면 true 반환")
		void 닉네임이_중복된다면_true_반환() throws Exception {
			// given
			String name = "세잇";
			given(memberService.checkDuplicateName(name))
					.willReturn(true);

			// when, then
			mockMvc.perform(get("/api/v1/member/duplicate/" + name)
							.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(content().string("true"))
					.andDo(print());
		}

		@Test
		@DisplayName("닉네임이 중복되지 않는다면 false 반환")
		void 닉네임이_중복되지_않는다면_false_반환() throws Exception {
			// given
			String name = "세잇";
			given(memberService.checkDuplicateName(name))
					.willReturn(false);

			// when, then
			mockMvc.perform(get("/api/v1/member/duplicate/" + name)
							.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(content().string("false"))
					.andDo(print());
		}
	}

	@Nested
	@DisplayName("회원 정보를 조회할 때")
	class 회원_정보를_조회할_때 {
		@Test
		@DisplayName("정상적이라면 회원 정보를 반환한다.")
		void 정상적이라면_회원_정보를_반환한다() throws Exception {
			// given
			MemberDto memberDto = MemberDto.builder()
					.id(1723L)
					.name("세잇")
					.build();
			given(memberService.getMemberInfo())
					.willReturn(memberDto);

			// when, then
			mockMvc.perform(get("/api/v1/member/info/")
							.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").value(1723L))
					.andExpect(jsonPath("$.name").value("세잇"))
					.andDo(print());
		}
	}
}