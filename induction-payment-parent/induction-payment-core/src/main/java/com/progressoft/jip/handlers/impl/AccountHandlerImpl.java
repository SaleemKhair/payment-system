package com.progressoft.jip.handlers.impl;

import java.math.BigDecimal;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.exceptions.AccountDoesNotHaveEnoughBalanceException;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.validators.Validator;

public class AccountHandlerImpl implements AccountHandler {

	private Validator<Account, ValidationException> validator;

	public AccountHandlerImpl(Validator<Account, ValidationException> validator) {
		this.validator = validator;
	}

	@Override
	public void debit(Account account, Amount amount) {
		String currencyCode = account.getCurrencyCode();
		BigDecimal debitAmount = amount.valueIn(currencyCode);
		if (!hasEnoughBalance(debitAmount.doubleValue(), account.getBalance()))
			throw new AccountDoesNotHaveEnoughBalanceException();
		account.setBalance(account.getBalance().subtract(debitAmount));
	}

	@Override
	public void credit(Account account, Amount amount) {
		String currencyCode = account.getCurrencyCode();
		BigDecimal creditAmount = amount.valueIn(currencyCode);
		account.setBalance(account.getBalance().add(creditAmount));
	}

	private boolean hasEnoughBalance(double debitAmount, BigDecimal balance) {
		return debitAmount <= balance.doubleValue();
	}

	@Override
	public void validateAccount(Account account) throws ValidationException {
		validator.validate(account);
	}

}
