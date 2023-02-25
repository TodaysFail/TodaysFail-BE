package com.todaysfailbe.member.service;

import org.springframework.stereotype.Service;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.model.request.CreateMemberRequest;
import com.todaysfailbe.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public void createMember(CreateMemberRequest request) {
		log.info("[MemberService.createMember] 회원 생성 요청: {}", request);
		memberRepository.save(Member.from(request));
		log.info("[MemberService.createMember] 회원 생성 성공");
	}
}
