package com.progressoft.jip.report;

import java.io.PrintStream;

import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.exceptions.RepositoryException;

public class ReportProvider {

    private PaymentRequestRepository paymentRequestRepository;

    public ReportProvider(PaymentRequestRepository paymentRequestRepository) {
	this.paymentRequestRepository = paymentRequestRepository;
    }

    public void print(String orderingAccountIBAN, ReportWriter reportWriter, PrintStream writer) throws RepositoryException {
	reportWriter.write(orderingAccountIBAN,
		paymentRequestRepository.loadPaymentRequestsByOrderingAccountIban(orderingAccountIBAN), writer);
    }

}
