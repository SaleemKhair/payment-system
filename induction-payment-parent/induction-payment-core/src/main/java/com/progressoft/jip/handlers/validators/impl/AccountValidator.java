package com.progressoft.jip.handlers.validators.impl;

import java.math.BigDecimal;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.gateways.exceptions.InvalidBalanceException;
import com.progressoft.jip.handlers.exceptions.AccountValidationException;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.validators.Validator;
import com.progressoft.jip.iban.exception.InvalidIBANException;

public class AccountValidator implements Validator<Account, ValidationException> {

	private Validator<String, InvalidIBANException> ibanValidator;

	public AccountValidator(Validator<String, InvalidIBANException> ibanValidator) {
		this.ibanValidator = ibanValidator;
	}

	@Override
	public void validate(Account account) throws InvalidIBANException, AccountValidationException {
		// TODO just replace it with ibanValidator.validate(iban);
		validateIban(account.getIban());
		validateBalance(account.getBalance());
	}

	private void validateIban(String iban) throws InvalidIBANException {
		ibanValidator.validate(iban);
	}

	private void validateBalance(BigDecimal balance) {
		if (balance.doubleValue() < 100) {
			throw new InvalidBalanceException();
		}
	}

}
