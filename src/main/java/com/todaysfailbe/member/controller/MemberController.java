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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@ApiOperation(
			value = "회원가입",
			notes = "닉네임 중복체크 후 중복이 아니라면 회원가입을 진행합니다"
	)
	@ApiResponses({
			@ApiResponse(
					code = 201, message = "API 정상 작동 / 회원가입 완료"
			),
			@ApiResponse(
					code = 400, message = "중복 된 닉네임이 있을 경우입니다"
			)
	})
	@PostMapping
	public ResponseEntity<Void> createMember(@RequestBody @Valid CreateMemberRequest createMemberRequest) {
		log.info("[MemberController.createMember] 회원 생성 요청: {}", createMemberRequest);
		memberService.createMember(createMemberRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(
			value = "닉네임 중복 체크",
			notes = "닉네임 중복체크를 진행합니다. 중복되었다면 true, 중복되지 않았다면 false를 반환합니다"
	)
	@ApiResponses({
			@ApiResponse(
					code = 200, message = "API 정상 작동 / 중복확인 완료"
			)
	})
	@GetMapping("/duplicate/{name}")
	public ResponseEntity<Boolean> checkDuplicateName(@PathVariable String name) {
		log.info("[MemberController.checkDuplicateName] 중복 이름 체크 요청: {}", name);
		return ResponseEntity.ok(memberService.checkDuplicateName(name));
	}
}
