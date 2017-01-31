package com.progressoft.jip.usecases;

import java.util.Collection;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.handlers.exceptions.ValidationException;

public interface AccountUseCases {

	void createAccount(Account account) throws ValidationException;

	Collection<AccountView> getAllAccounts();

	AccountView getAccountByIban(String iban);

	void editAccountStatus(String status, String iban) throws ValidationException;

	void editAccountRule(String rule, String iban) throws ValidationException;

	void editAccountType(String type, String iban) throws ValidationException;

	void editAccount(Account account) throws ValidationException;
}
