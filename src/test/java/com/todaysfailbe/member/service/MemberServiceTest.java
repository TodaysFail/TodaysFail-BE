package com.todaysfailbe.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.todaysfailbe.common.utils.EncryptUtil;
import com.todaysfailbe.common.utils.SessionUtil;
import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.exception.DuplicateNameException;
import com.todaysfailbe.member.model.request.CreateMemberRequest;
import com.todaysfailbe.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MemberServiceTest {
	@Mock
	private MemberRepository memberRepository;

	@Mock
	private EncryptUtil encryptUtil;

	@Mock
	private SessionUtil sessionUtil;

	@InjectMocks
	private MemberService memberService;

	@Mock
	private Member mockMember;

	@Nested
	@DisplayName("회원을 생성할 때")
	class 회원을_생성할_때 {
		@Test
		@DisplayName("닉네임이 중복되었다면 예외를 발생시킨다.")
		void 닉네임이_중복되었다면_예외를_발생시킨다() {
			CreateMemberRequest createMemberRequest = mock(CreateMemberRequest.class);
			given(createMemberRequest.getName()).willReturn("주니");

			given(memberRepository.existsByName(anyString()))
					.willReturn(true);

			ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

			// when, then
			assertThatThrownBy(() -> memberService.createMember(createMemberRequest))
					.isInstanceOf(DuplicateNameException.class);
			verify(memberRepository, times(0)).save(captor.capture());
			verify(memberRepository, times(1)).existsByName(anyString());
		}

		@Test
		@DisplayName("정상적이라면 예외를 발생시키지 않는다.")
		void 정상적이라면_예외를_발생시키다지_않는다() {
			// given
			CreateMemberRequest createMemberRequest = mock(CreateMemberRequest.class);
			given(createMemberRequest.getName()).willReturn("주니");
			given(createMemberRequest.getPassword()).willReturn("1234");

			given(encryptUtil.encrypt(anyString()))
					.willReturn("encrypted password");
			given(memberRepository.save(any()))
					.willReturn(mockMember);

			ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

			// when, then
			assertThatCode(() -> memberService.createMember(createMemberRequest))
					.doesNotThrowAnyException();
			verify(memberRepository, times(1)).save(captor.capture());
		}
	}

	@Nested
	@DisplayName("닉네임 중복체크할 때")
	class 닉네임_중복체크할_때 {
		@Test
		@DisplayName("중복된다면 true를 반환한다.")
		void 중복된다면_true를_반환한다() {
			// given
			given(memberRepository.existsByName(anyString()))
					.willReturn(true);

			// when
			Boolean result = memberService.checkDuplicateName("지우");

			// then
			assertThat(result).isTrue();
			verify(memberRepository, times(1)).existsByName(anyString());
		}

		@Test
		@DisplayName("중복되지 않는다면 false를 반환한다.")
		void 중복되지_않는다면_false를_반환한다() {
			// given
			given(memberRepository.existsByName(anyString()))
					.willReturn(false);

			// when
			Boolean result = memberService.checkDuplicateName("지우");

			// then
			assertThat(result).isFalse();
			verify(memberRepository, times(1)).existsByName(anyString());
		}
	}
}