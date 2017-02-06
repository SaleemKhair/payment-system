package com.progressoft.jip.gateway.sql;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.behaviors.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.sql.AbstractGateway;
import com.progressoft.jip.gateways.views.AccountView;

public class MySqlAccountGateway extends AbstractGateway implements AccountGateway {

	private Behavior<AccountView> loadAccountByIBAN;
	private Behavior<Collection<AccountView>> loadAccounts;
	private Behavior<Void> updateAccount;
	private Behavior<Void> createAccount;

	public MySqlAccountGateway(DataSource dataSource, AccountGatewayDBBehaviorsFactory factory) {
		super(dataSource);
		this.loadAccountByIBAN = factory.loadAccountByIBAN();
		this.loadAccounts = factory.loadAccounts();
		this.updateAccount = factory.updateAccount();
		this.createAccount = factory.createAccount();

	}

	@Override
	public AccountView loadAccountByIban(String iban) {
		return loadAccountByIBAN.execute(dataSource, iban);
	}

	@Override
	public Collection<AccountView> loadAccounts() {
		return loadAccounts.execute(dataSource);
	}

	@Override
	public void updateAccount(AccountView account) {
		updateAccount.execute(dataSource, account);
	}

	@Override
	public void createAccount(AccountView newAccount) {
		createAccount.execute(dataSource, newAccount);
	}

}
