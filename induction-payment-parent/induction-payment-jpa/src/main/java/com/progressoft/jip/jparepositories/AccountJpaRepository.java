package com.progressoft.jip.jparepositories;

import java.util.Collection;

import com.progressoft.jip.entities.AccountEntity;

public interface AccountJpaRepository {

	public AccountEntity loadAccountByIban(String iban);

	public Collection<AccountEntity> loadAccounts();

	void updateAccount(AccountEntity account);

	void createAccount(AccountEntity newAccount);

}