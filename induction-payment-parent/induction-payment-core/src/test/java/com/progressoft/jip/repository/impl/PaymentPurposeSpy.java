package com.progressoft.jip.repository.impl;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.gateways.views.PaymentPurposeView;

public class PaymentPurposeSpy extends PaymentPurpose {

	private static final long serialVersionUID = 1L;
	private PaymentPurposeView dataStructure;

	public PaymentPurposeSpy(PaymentPurposeView dataStructure) {
		this.dataStructure = dataStructure;

	}

	public String getCode() {
		return dataStructure.getCode();
	}

	public String getName() {
		return dataStructure.getName();
	}
}
