package com.todaysfailbe.member.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysfailbe.member.model.request.CreateMemberRequest;
import com.todaysfailbe.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<Void> createMember(@RequestBody @Valid CreateMemberRequest request) {
		log.info("[MemberController.createMember] 회원 생성 요청: {}", request);
		memberService.createMember(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/duplicate/{name}")
	public ResponseEntity<Boolean> checkDuplicateName(@PathVariable String name) {
		log.info("[MemberController.checkDuplicateName] 중복 이름 체크 요청: {}", name);
		return ResponseEntity.ok(memberService.checkDuplicateName(name));
	}
}
