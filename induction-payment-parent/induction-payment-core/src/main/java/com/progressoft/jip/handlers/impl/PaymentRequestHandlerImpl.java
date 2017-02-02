package com.progressoft.jip.handlers.impl;

import java.time.LocalDate;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.PaymentRequestHandler;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.validators.Validator;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public class PaymentRequestHandlerImpl implements PaymentRequestHandler {

	private CurrencyExchangeRateRepository currencyExchangeRateRepository;
	private Validator<PaymentRequest, ValidationException> validator;

	public PaymentRequestHandlerImpl(CurrencyExchangeRateRepository currencyExchangeRateRepository,
			Validator<PaymentRequest, ValidationException> validator) {
		this.currencyExchangeRateRepository = currencyExchangeRateRepository;
		this.validator = validator;

	}

	@Override
	public boolean isDueDate(LocalDate paymentDate) {
		LocalDate now = LocalDate.now();
		return now.isAfter(paymentDate) || now.isEqual(paymentDate);
	}

	@Override
	public void preformPayment(PaymentRequest paymentRequest, AccountHandler accountHandler, Account account) {
		accountHandler.debit(account, new Amount(currencyExchangeRateRepository, paymentRequest.getPaymentAmount(),
				paymentRequest.getCurrencyCode()));
	}

	@Override
	public void validatePaymentRequest(PaymentRequest paymentRequest, Account account) throws ValidationException {
		validator.validate(paymentRequest);
	}

	@Override
	public void fillAmountInWords(PaymentRequest paymentRequest, AbstractAmountWriter abstractAmountWriter,
			String writerKey) {
		String amountInWords = abstractAmountWriter.writeAmount(writerKey, paymentRequest.getCurrencyCode(),
				paymentRequest.getPaymentAmount());
		paymentRequest.setAmountInWords(amountInWords);
	}

	@Override
	public void setPaymentRequestStatus(PaymentRequest paymentRequest) {
		paymentRequest.setPaymentStatus("created");
	}

}
