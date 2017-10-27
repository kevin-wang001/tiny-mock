package com.kvn.domain;

import java.math.BigDecimal;

/**
* @author wzy
* @date 2017年10月27日 下午4:20:37
*/
public class RefundOrderVo {
	private BigDecimal refundableAmount;
	private Long refundCompanyId;
	private String refundCompanyName;
	public BigDecimal getRefundableAmount() {
		return refundableAmount;
	}
	public void setRefundableAmount(BigDecimal refundableAmount) {
		this.refundableAmount = refundableAmount;
	}
	public Long getRefundCompanyId() {
		return refundCompanyId;
	}
	public void setRefundCompanyId(Long refundCompanyId) {
		this.refundCompanyId = refundCompanyId;
	}
	public String getRefundCompanyName() {
		return refundCompanyName;
	}
	public void setRefundCompanyName(String refundCompanyName) {
		this.refundCompanyName = refundCompanyName;
	}

}
