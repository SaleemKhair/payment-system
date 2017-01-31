package com.progressoft.jip.usecases.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.usecases.AccountUseCases;

public class AccountUseCasesImpl implements AccountUseCases {

	private AccountRepository accountRepository;
	private AccountHandler accountHandler;

	public AccountUseCasesImpl(AccountRepository accountRepository, AccountHandler accountHandler) {
		this.accountRepository = accountRepository;
		this.accountHandler = accountHandler;
	}

	@Override
	public void createAccount(Account account) throws ValidationException {
		accountHandler.validateAccount(account);
		accountRepository.createAccount(account);
	}

	@Override
	public Collection<AccountView> getAllAccounts() {
		Collection<Account> loadAccounts = accountRepository.loadAccounts();
		List<AccountView> accounts = new ArrayList<>();
		accounts.addAll(loadAccounts);
		return accounts;
	}

	@Override
	public AccountView getAccountByIban(String iban) {
		return accountRepository.loadAccountByIban(iban);
	}

	@Override
	public void editAccountStatus(String status, String iban) throws ValidationException {
		Account account = accountRepository.loadAccountByIban(iban);
		account.setStatus(status);
		accountHandler.validateAccount(account);
		accountRepository.updateAccount(account);
	}

	@Override
	public void editAccountRule(String rule, String iban) throws ValidationException {
		Account account = accountRepository.loadAccountByIban(iban);
		account.setRule(rule);
		accountHandler.validateAccount(account);
		accountRepository.updateAccount(account);
	}

	@Override
	public void editAccountType(String type, String iban) throws ValidationException {
		Account account = accountRepository.loadAccountByIban(iban);
		account.setType(type);
		accountHandler.validateAccount(account);
		accountRepository.updateAccount(account);
	}

	@Override
	public void editAccount(Account account) throws ValidationException {
		accountHandler.validateAccount(account);
		accountRepository.updateAccount(account);
	}
}
