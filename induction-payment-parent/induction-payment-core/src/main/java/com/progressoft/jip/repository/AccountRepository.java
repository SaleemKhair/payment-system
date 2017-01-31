package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.beans.Account;

public interface AccountRepository {

	Account loadAccountByIban(String string);

	Collection<Account> loadAccounts();

	void updateAccount(Account account);

	void createAccount(Account account);

}