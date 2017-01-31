package com.progressoft.jip.report;

import java.io.PrintStream;
import java.util.Collection;

import com.progressoft.jip.beans.PaymentRequest;

@FunctionalInterface
public interface ReportWriter {
	void write(String accountIBAN, Collection<PaymentRequest> paymentRequests, PrintStream writer);
}
