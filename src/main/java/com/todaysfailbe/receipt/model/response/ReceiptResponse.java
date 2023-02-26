package com.todaysfailbe.receipt.model.response;

import java.util.List;

import com.todaysfailbe.receipt.model.ReceiptDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ApiModel
@ToString
@NoArgsConstructor
public class ReceiptResponse {
	@ApiParam(value = "실패 기록 총 개수", required = true, example = "19")
	private Integer total;

	@ApiParam(value = "실패 기록 목록", required = true)
	private List<ReceiptDto> receiptList;

	@ApiParam(value = "영수증 날짜", required = true, example = "2023-02-26T19:31:51.069561")
	private String date;

	private ReceiptResponse(Integer total, List<ReceiptDto> receiptList, String date) {
		this.total = total;
		this.receiptList = receiptList;
		this.date = date;
	}

	public static ReceiptResponse from(List<ReceiptDto> receiptList, String date) {
		return new ReceiptResponse(receiptList.size(), receiptList, date);
	}
}
