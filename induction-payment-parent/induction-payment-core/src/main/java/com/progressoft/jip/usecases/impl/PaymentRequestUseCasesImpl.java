package com.progressoft.jip.usecases.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.progressoft.jip.handlers.PaymentRequestHandler;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.report.ReportProvider;
import com.progressoft.jip.report.impl.CSVReportWriter;
import com.progressoft.jip.report.impl.XMLReportWriter;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.exceptions.RepositoryException;
import com.progressoft.jip.usecases.PaymentRequestUseCases;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public class PaymentRequestUseCasesImpl implements PaymentRequestUseCases {

	private PaymentRequestHandler paymentRequestHandler;
	private AccountRepository accountRepository;
	private PaymentRequestRepository paymentRequestRepository;
	private ReportProvider reportProvider;

	public PaymentRequestUseCasesImpl(PaymentRequestHandler paymentRequestHandler, AccountRepository accountRepository,
			PaymentRequestRepository paymentRequestRepository, ReportProvider reportProvider) {
		this.paymentRequestHandler = paymentRequestHandler;
		this.accountRepository = accountRepository;
		this.paymentRequestRepository = paymentRequestRepository;
		this.reportProvider = reportProvider;

	}

	@Override
	public void createPaymentRequest(PaymentRequest paymentRequest, AbstractAmountWriter amountWriter, String writerKey)
			throws ValidationException {
		Account orderingAccount = accountRepository.loadAccountByIban(paymentRequest.getOrderingAccountIban());
		paymentRequestHandler.validatePaymentRequest(paymentRequest, orderingAccount);
		paymentRequestHandler.fillAmountInWords(paymentRequest, amountWriter, writerKey);
		paymentRequestHandler.setPaymentRequestStatus(paymentRequest, "created");

		paymentRequestRepository.insertPaymentRequest(paymentRequest);
	}

	@Override
	public Collection<PaymentRequestView> getPaymentRequestsByOrderingAccountIban(String iban)
			throws RepositoryException {
		Collection<PaymentRequest> requestsByOrderingAccountIban = paymentRequestRepository
				.loadPaymentRequestsByOrderingAccountIban(iban);
		List<PaymentRequestView> requests = new ArrayList<>();
		requests.addAll(requestsByOrderingAccountIban);
		return requests;

	}

	@Override
	public void editPaymentRequestDate(LocalDate paymentDate, int paymentId) throws ValidationException {
		PaymentRequest paymentRequest = paymentRequestRepository.loadPaymentRequestById(paymentId);
		paymentRequest.setPaymentDate(Date.valueOf(paymentDate));
		Account account = accountRepository.loadAccountByIban(paymentRequest.getOrderingAccountIban());
		paymentRequestHandler.validatePaymentRequest(paymentRequest, account);
		paymentRequestRepository.updatePaymentRequest(paymentRequest);
	}

	@Override
	public void editPaymentRequestAmount(BigDecimal amount, int paymentId) throws ValidationException {
		PaymentRequest paymentRequest = paymentRequestRepository.loadPaymentRequestById(paymentId);
		paymentRequest.setPaymentAmount(amount);
		Account account = accountRepository.loadAccountByIban(paymentRequest.getOrderingAccountIban());
		paymentRequestHandler.validatePaymentRequest(paymentRequest, account);
		paymentRequestRepository.updatePaymentRequest(paymentRequest);
	}

	@Override
	public void editPaymentRequestPurpose(String paymentPurpose, int paymentId) throws ValidationException {
		PaymentRequest paymentRequest = paymentRequestRepository.loadPaymentRequestById(paymentId);
		paymentRequest.setPurposeCode(paymentPurpose);
		Account account = accountRepository.loadAccountByIban(paymentRequest.getOrderingAccountIban());
		paymentRequestHandler.validatePaymentRequest(paymentRequest, account);
		paymentRequestRepository.updatePaymentRequest(paymentRequest);
	}

	@Override
	public void executePayment(PaymentRequestView paymentRequest) {
		// TODO if submit was manual nothing to do yet

	}

	@Override
	public PaymentRequestView getPaymentRequestById(int id) {
		return paymentRequestRepository.loadPaymentRequestById(id);
	}

	@Override
	public String generateReport(String iban, String format) throws RepositoryException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		if ("xml".equalsIgnoreCase(format)) {
			reportProvider.print(iban, new XMLReportWriter(), printStream);
		} else if ("csv".equalsIgnoreCase(format))
			reportProvider.print(iban, new CSVReportWriter(), printStream);

		return new String(baos.toByteArray(), StandardCharsets.UTF_8);

	}

}
