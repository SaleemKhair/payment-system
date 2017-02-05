package com.progressoft.jip.handlers;

import java.time.LocalDate;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.exceptions.PaymentValidationException;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public interface PaymentRequestHandler {

	void validatePaymentRequest(PaymentRequest paymentRequest, Account account)
			throws PaymentValidationException, ValidationException;

	boolean isDueDate(LocalDate paymentDate);

	void preformPayment(PaymentRequest paymentRequest, AccountHandler accountHandler, Account account);

	public void fillAmountInWords(PaymentRequest paymentRequest, AbstractAmountWriter abstractAmountWriter,
			String writerKey);

	public void setPaymentRequestStatus(PaymentRequest paymentRequest, String paymentStatus);

	void setSubmissionState(PaymentRequest paymentRequest, String submissionState);

}