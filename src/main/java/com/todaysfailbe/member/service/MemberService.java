package com.todaysfailbe.member.service;

import org.springframework.stereotype.Service;

import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.exception.DuplicateNameException;
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
		if (checkDuplicateName(request.getName())) {
			log.info("[MemberService.createMember] {}는이미 존재하는 이름입니다.", request.getName());
			throw new DuplicateNameException("이미 존재하는 이름입니다.");
		}
		memberRepository.save(Member.from(request));
		log.info("[MemberService.createMember] 회원 생성 성공");
	}

	public Boolean checkDuplicateName(String name) {
		log.info("[MemberService.checkDuplicateName] 중복 이름 체크 요청: {}", name);
		return memberRepository.existsByName(name);
	}
}
