package com.todaysfailbe.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.model.request.CreateMemberRequest;
import com.todaysfailbe.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MemberServiceTest {
	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private MemberService memberService;

	@Mock
	private Member mockMember;

	@Test
	@DisplayName("회원을 생성한다.")
	void 회원을_생성한다() {
		// given
		CreateMemberRequest createMemberRequest = mock(CreateMemberRequest.class);
		given(createMemberRequest.getName()).willReturn("주니");

		given(memberRepository.save(any()))
				.willReturn(mockMember);

		ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

		// when, then
		assertThatCode(() -> memberService.createMember(createMemberRequest))
				.doesNotThrowAnyException();
		verify(memberRepository, times(1)).save(captor.capture());
	}

}