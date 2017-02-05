package com.progressoft.jip.handlers.impl;

import java.math.BigDecimal;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.validators.Validator;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;

public class AccountHandlerImpl implements AccountHandler {

	private Validator<Account, ValidationException> validator;

	public AccountHandlerImpl(Validator<Account, ValidationException> validator) {
		this.validator = validator;
	}

	@Override
	public void debit(Account account, PaymentRequest paymentRequest,
			CurrencyExchangeRateRepository currencyExchangeRateRepository) {
		String currencyCode = account.getCurrencyCode();
		Amount amount = new Amount(currencyExchangeRateRepository, paymentRequest.getPaymentAmount(),
				paymentRequest.getCurrencyCode());
		BigDecimal debitAmount = amount.valueIn(currencyCode);
		paymentRequest.setSubmissionState("Submitted");
		if (!hasEnoughBalance(debitAmount.doubleValue(), account.getBalance())) {
			paymentRequest.setPaymentStatus("Rejected");
		} else {
			paymentRequest.setPaymentStatus("Approved");
			account.setBalance(account.getBalance().subtract(debitAmount));
		}
	}

	private boolean hasEnoughBalance(double debitAmount, BigDecimal balance) {
		return debitAmount <= balance.doubleValue();
	}

	@Override
	public void credit(Account account, PaymentRequest paymentRequest,
			CurrencyExchangeRateRepository currencyExchangeRateRepository) {
		String currencyCode = account.getCurrencyCode();
		Amount amount = new Amount(currencyExchangeRateRepository, paymentRequest.getPaymentAmount(),
				paymentRequest.getCurrencyCode());
		BigDecimal creditAmount = amount.valueIn(currencyCode);
		account.setBalance(account.getBalance().add(creditAmount));
	}

	@Override
	public void validateAccount(Account account) throws ValidationException {
		validator.validate(account);
	}

	@Override
	public boolean checkAccountHasEnoughBalance(AccountView accountView, Amount paymentAmount) {
		BigDecimal amount = paymentAmount.getAmount();
		if (!paymentAmount.getCurrencyCode().equals(accountView.getCurrencyCode())) {
			amount = paymentAmount.valueIn(accountView.getCurrencyCode());
		}
		return accountView.getBalance().doubleValue() >= amount.doubleValue();
	}

}
