package com.todaysfailbe.record.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysfailbe.record.domain.Record;
import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.model.request.RecordsRequest;
import com.todaysfailbe.record.service.RecordService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {
	private final RecordService recordService;

	@ApiOperation(
			value = "실패 기록",
			notes = "실패 기록 요청을 받아 저장합니다"
	)
	@ApiResponses({
			@ApiResponse(
					code = 201, message = "API 정상 작동 / 실패 기록 저장 완료"
			),
			@ApiResponse(
					code = 400, message = "입력 값이 잘못되었을 경우입니다"
			)
	})
	@PostMapping
	public ResponseEntity<Void> createRecord(@RequestBody @Valid CreateRecordRequest createRecordRequest) {
		log.info("[RecordController.createRecord] 레코드 생성 요청");
		recordService.createRecord(createRecordRequest);
		log.info("[RecordController.createRecord] 레코드 생성 성공");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(
			value = "실패 기록들을 조회합니다",
			notes = "닉네임을 받아 해당 닉네임의 실패 기록들을 조회합니다"
	)
	@ApiResponses({
			@ApiResponse(
					code = 201, message = "API 정상 작동 / 실패 기록 목록 조회 성공"
			),
			@ApiResponse(
					code = 400, message = "입력 값이 잘못되었을 경우입니다"
			)
	})
	@GetMapping
	public ResponseEntity<ConcurrentMap<LocalDate, List<Record>>> getRecords(
			@RequestBody @Valid RecordsRequest recordsRequest) {
		log.info("[RecordController.getRecords] 레코드 목록 조회 요청");
		ConcurrentMap<LocalDate, List<Record>> response = recordService.getRecords(recordsRequest);
		log.info("[RecordController.getRecords] 레코드 목록 조회 성공");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
