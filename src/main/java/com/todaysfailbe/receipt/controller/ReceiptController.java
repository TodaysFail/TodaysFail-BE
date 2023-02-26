package com.todaysfailbe.receipt.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysfailbe.receipt.model.CreateReceiptRequest;
import com.todaysfailbe.receipt.service.ReceiptService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/receipt")
@RequiredArgsConstructor
public class ReceiptController {
	private final ReceiptService receiptService;

	@ApiOperation(
			value = "영수증 등록",
			notes = "날짜를 입력받아 해당 날짜에 해당하는 영수증을 등록합니다"
	)
	@ApiResponses({
			@ApiResponse(
					code = 201, message = "API 정상 작동 / 영수증 등록 완료"
			),
			@ApiResponse(
					code = 400, message = "중복 된 닉네임이 있을 경우입니다"
			)
	})
	@PostMapping
	ResponseEntity<Long> createReceipt(@RequestBody @Valid CreateReceiptRequest createReceiptRequest) {
		log.info("[ReceiptController.createReceipt] 영수증 등록 요청");
		Long receiptId = receiptService.createReceipt(createReceiptRequest);
		log.info("[ReceiptController.createReceipt] 영수증 등록 완료: {}", receiptId);
		return ResponseEntity.status(HttpStatus.CREATED).body(receiptId);
	}
}
