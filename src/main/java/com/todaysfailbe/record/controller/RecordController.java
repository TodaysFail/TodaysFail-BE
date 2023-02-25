package com.todaysfailbe.record.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysfailbe.record.model.request.CreateRecordRequest;
import com.todaysfailbe.record.service.RecordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {
	private final RecordService recordService;

	@PostMapping
	public ResponseEntity<Void> createRecord(@RequestBody @Valid CreateRecordRequest request) {
		log.info("[RecordController.createRecord] 레코드 생성 요청");
		recordService.createRecord(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
