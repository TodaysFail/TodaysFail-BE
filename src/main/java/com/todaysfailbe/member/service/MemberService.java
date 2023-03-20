package com.todaysfailbe.member.service;

import org.springframework.stereotype.Service;

import com.todaysfailbe.common.utils.EncryptUtil;
import com.todaysfailbe.common.utils.SessionUtil;
import com.todaysfailbe.member.domain.Member;
import com.todaysfailbe.member.exception.DuplicateNameException;
import com.todaysfailbe.member.model.request.CreateMemberRequest;
import com.todaysfailbe.member.model.request.LoginMemberRequest;
import com.todaysfailbe.member.model.response.MemberDto;
import com.todaysfailbe.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final EncryptUtil encryptUtil;
	private final SessionUtil sessionUtil;

	public void createMember(CreateMemberRequest request) {
		log.info("[MemberService.createMember] 회원 생성 요청: {}", request);
		if (checkDuplicateName(request.getName())) {
			log.info("[MemberService.createMember] {}는이미 존재하는 이름입니다.", request.getName());
			throw new DuplicateNameException("이미 존재하는 이름입니다.");
		}
		request.setPassword(encryptUtil.encrypt(request.getPassword()));
		Member member = memberRepository.save(Member.from(request));
		sessionUtil.saveUserIdInSession(member.getId());
		log.info("[MemberService.createMember] 회원 생성 성공");
	}

	public Boolean checkDuplicateName(String name) {
		log.info("[MemberService.checkDuplicateName] 중복 이름 체크 요청: {}", name);
		return memberRepository.existsByName(name);
	}

	public void loginMember(LoginMemberRequest request) {
		log.info("[MemberService.loginMember] 회원 로그인 요청: {}", request);
		request.setPassword(encryptUtil.encrypt(request.getPassword()));
		Member member = memberRepository.findByNameAndPassword(request.getName(), request.getPassword())
				.orElseThrow(() -> {
					log.info("[MemberService.loginMember] 회원 로그인 실패 - 회원 정보 불일치: {}", request.getName());
					throw new IllegalArgumentException("로그인에 실패하였습니다.");
				});
		sessionUtil.saveUserIdInSession(member.getId());
		log.info("[MemberService.loginMember] 회원 로그인 성공: {}", request.getName());
	}

	public void logoutMember() {
		log.info("[MemberService.loginMember] 회원 로그아웃 요청");
		sessionUtil.invalidateSession();
		log.info("[MemberService.loginMember] 회원 로그아웃 성공");
	}

	public MemberDto getMemberInfo() {
		log.info("[MemberService.getMemberInfo] 회원 정보 조회 요청");
		Long userIdBySession = sessionUtil.findUserIdBySession();
		Member member = memberRepository.findById(userIdBySession)
				.orElseThrow(() -> {
					log.info("[MemberService.getMemberInfo] 회원 정보 조회 실패 - 존재하지 않는 회원: {}", userIdBySession);
					throw new IllegalArgumentException("존재하지 않는 회원입니다.");
				});
		MemberDto memberDto = MemberDto.from(member);
		log.info("[MemberService.getMemberInfo] 회원 정보 조회 성공: {}", memberDto);
		return memberDto;
	}
}
