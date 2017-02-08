package com.progressoft.jip.handlers;

import com.progressoft.jip.beans.PaymentRequest;

public interface PaymentImportHandler {

	public void onReportStart();

	void onSuccess();

	void onReportEnd();

	String displayReport();

	void accept(int rowNumber, PaymentRequest paymentRequest);

}
