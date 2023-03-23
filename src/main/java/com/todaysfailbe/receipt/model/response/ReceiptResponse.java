package com.todaysfailbe.receipt.model.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "실패 기록 총 개수", required = true, example = "19")
	private Integer total;

	@ApiModelProperty(value = "실패 기록 목록", required = true)
	private List<ReceiptDto> receiptList;

	@ApiModelProperty(value = "영수증 날짜", required = true, example = "FEBRUARY 02 - 26, 2023")
	private String date;

	@ApiModelProperty(value = "영수증 번호", required = true, example = "567cf5b4-0080-486f-836d-22a5db1540de")
	private String uuid;

	@ApiModelProperty(value = "작성자의 닉네임", required = true, example = "도모")
	private String writerName;

	private ReceiptResponse(Integer total, List<ReceiptDto> receiptList, String date, String uuid, String writerName) {
		this.total = total;
		this.receiptList = receiptList;
		this.date = date;
		this.uuid = uuid;
		this.writerName = writerName;
	}

	public static ReceiptResponse from(List<ReceiptDto> receiptList, String date, String uuid, String writerName) {
		return new ReceiptResponse(receiptList.size(), receiptList, date, uuid, writerName);
	}
}
