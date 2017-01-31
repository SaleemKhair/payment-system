package com.progressoft.jip.beans;

import java.io.Serializable;

import com.progressoft.jip.gateways.views.PaymentPurposeView;

public class PaymentPurpose implements Serializable, PaymentPurposeView {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;

	public PaymentPurpose() {
		super();
	}

	public PaymentPurpose(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
