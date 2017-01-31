package com.progressoft.jip.gateways;

import java.util.Collection;

import com.progressoft.jip.gateways.views.AccountView;

public interface AccountGateway {

	public AccountView loadAccountByIban(String iban);

	public Collection<AccountView> loadAccounts();

	void updateAccount(AccountView account);

	void createAccount(AccountView newAccount);

}