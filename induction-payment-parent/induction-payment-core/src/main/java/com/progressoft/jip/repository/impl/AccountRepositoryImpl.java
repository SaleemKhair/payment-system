package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.exceptions.AccountNotFoundException;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.repository.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

	private AccountGateway accountGateway;

	public AccountRepositoryImpl(AccountGateway accountGateway) {
		this.accountGateway = accountGateway;
	}

	@Override
	public Account loadAccountByIban(String iban) {
		Account loaded = (Account) accountGateway.loadAccountByIban(iban);
		if (Objects.isNull(loaded))
			throw new AccountNotFoundException();
		return loaded;
	}

	@Override
	public Collection<Account> loadAccounts() {
		ArrayList<Account> collection = new ArrayList<>();
		accountGateway.loadAccounts().stream().forEach(a -> collection.add((Account) a));
		return Collections.unmodifiableCollection(collection);
	}

	@Override
	public void updateAccount(Account account) {
		accountGateway.updateAccount((AccountView) account);
	}

	@Override
	public void createAccount(Account account) {
		accountGateway.createAccount(account);
	}

}
