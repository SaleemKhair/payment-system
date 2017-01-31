package com.progressoft.jip.report.impl;

import java.io.PrintStream;
import java.util.Collection;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.report.ReportWriter;

public class CSVReportWriter implements ReportWriter {
	@Override
	public void write(String accountIBAN, Collection<PaymentRequest> paymentRequests, PrintStream stream) {
		stream.println("Ordering Account IBAN: " + accountIBAN);
		stream.printf("%-22s,%-34s,%-34s,%-16s,%-16s,%-16s,%-16s,%-64s,%-16s%n", "Payment Request Number",
				"Ordering IBAN", "Beneficiary IBAN", "Beneficiary Name", "Payment Amount", "Currency Code",
				"Purpose Code", "Amount In Words", "Payment Date");
		for (PaymentRequest request : paymentRequests) {
			stream.printf("%-22s,%-34s,%-34s,%-16s,%-16f,%-16s,%-16s,%-64s ,%-16s%n",
					"PaymentRequest-" + request.getId(), request.getOrderingAccountIban(),
					request.getBeneficiaryAccountIban(), request.getBeneficiaryName(), request.getPaymentAmount(),
					request.getCurrencyCode(), request.getPurposeCode(), request.getAmountInWords(),
					request.getPaymentDate().toString());
		}
	}

}
