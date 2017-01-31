package com.progressoft.jip.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.repository.exceptions.RepositoryException;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public interface PaymentRequestUseCases {

	PaymentRequestView getPaymentRequestById(int id);

	void createPaymentRequest(PaymentRequest paymentRequest, AbstractAmountWriter amountWriter, String writerKey)
			throws ValidationException;

	Collection<PaymentRequestView> getPaymentRequestsByOrderingAccountIban(String iban) throws RepositoryException;

	void editPaymentRequestDate(LocalDate paymentDate, int paymentId) throws ValidationException;

	void editPaymentRequestAmount(BigDecimal amount, int paymentId) throws ValidationException;

	void editPaymentRequestPurpose(String paymentPurpose, int paymentId) throws ValidationException;

	void executePayment(PaymentRequestView paymentRequest);

	String generateReport(String iban, String format) throws RepositoryException;

}