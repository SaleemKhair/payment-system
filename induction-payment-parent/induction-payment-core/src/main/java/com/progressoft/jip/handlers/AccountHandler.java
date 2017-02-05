package com.progressoft.jip.handlers;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;

public interface AccountHandler {

	void debit(Account account, PaymentRequest paymentRequestView,CurrencyExchangeRateRepository currencyExchangeRateRepository);

	void credit(Account account, PaymentRequest paymentRequestView,CurrencyExchangeRateRepository currencyExchangeRateRepository);

	void validateAccount(Account account) throws ValidationException;
	
	boolean checkAccountHasEnoughBalance(AccountView accountView,Amount paymentAmount );

}