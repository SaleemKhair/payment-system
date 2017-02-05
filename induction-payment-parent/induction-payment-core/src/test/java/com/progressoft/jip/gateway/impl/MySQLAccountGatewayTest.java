package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.behaviors.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.exceptions.AccountNotFoundException;
import com.progressoft.jip.gateways.exceptions.InvalidBalanceException;
import com.progressoft.jip.gateways.exceptions.NoAccountInsertedException;
import com.progressoft.jip.gateways.exceptions.NullAccountIBANException;
import com.progressoft.jip.gateways.sql.impl.MySqlAccountGateway;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.utilities.DataBaseSettings;

public class MySQLAccountGatewayTest {

	private static final String ACTIVE = "ACTIVE";
	private static final String IBAN = "JO94CBJO0010000000000131000302";
	private AccountGateway accountsGateway;
	private Account accountDataStructure = new Account();

	@Before
	public void setUp() {

		BasicDataSource datasource = connectionConfiguration();
		accountsGateway = new MySqlAccountGateway(datasource, new AccountGatewayDBBehaviorsFactoryImpl());
		try {
			new QueryRunner(datasource).update("delete from ACCOUNT where IBAN='JO94CBJO0010000000000131000399'");
		} catch (SQLException e) {
			throw new IllegalStateException("Couldn't prepare database", e);
		}

	}

	private BasicDataSource connectionConfiguration() {
		BasicDataSource dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
		return dataSource;
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
		accountsGateway.loadAccountByIban(null);
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
		accountsGateway.loadAccountByIban("");
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingUnavailableIBANCode_ShouldThrowNullAccountIBAN() {
		accountsGateway.loadAccountByIban("ekjsfnefk");
	}

	@Test
	public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
		AccountView accounts = accountsGateway.loadAccountByIban(IBAN);
		assertEquals(IBAN, accounts.getIban());
	}

	@Test
	public void givenMySQLAccountGateway_CallingUpdateAcount_PassingExistingAccount_ThenCallingLoadAccountByIBAN_ShouldReturnUpdatedAccount() {
		Account originalAccount = new Account();
		originalAccount.setIban(IBAN);
		originalAccount.setType("TYPE");
		BigDecimal newBalance = BigDecimal.valueOf(Math.random() * 500);
		originalAccount.setBalance(newBalance);
		originalAccount.setStatus(ACTIVE);
		originalAccount.setCurrencyCode("USD");
		originalAccount.setRule("five.months.ahead");
		accountsGateway.updateAccount(originalAccount);
		AccountView updatedAccount = accountsGateway.loadAccountByIban(IBAN);
		assertTrue(Math.abs(newBalance.doubleValue() - updatedAccount.getBalance().doubleValue()) <= 1e-3);
	}

	@Test(expected = InvalidBalanceException.class)
	public void givenMySQLAccountGateway_CallingUpdateAcount_PassingNegativeBalance_ShouldThrowInvalidBalance() {
		Account originalAccount = new Account();
		originalAccount.setIban(IBAN);
		originalAccount.setType("TYPE");
		originalAccount.setBalance(BigDecimal.valueOf(-200));
		originalAccount.setStatus(ACTIVE);
		accountsGateway.updateAccount(originalAccount);
	}

	@Test
	public void givenMySQLAccountGateway_CallingInsertAccount_ShouldCreateAccount() {
		accountDataStructure.setIban("JO94CBJO0010000000000131000399");
		accountDataStructure.setBalance(BigDecimal.valueOf(500));
		accountDataStructure.setCurrencyCode("JOD");
		accountDataStructure.setType("investment");
		accountDataStructure.setStatus(ACTIVE);
		accountDataStructure.setRule("five.months.ahead");
		accountsGateway.createAccount(accountDataStructure);
		assertEquals(accountDataStructure.getIban(),
				accountsGateway.loadAccountByIban("JO94CBJO0010000000000131000399").getIban());
	}

	@Test(expected = NoAccountInsertedException.class)
	public void givenMySQLAccountGateway_CallingInsertAccount_PassingInvalidData_ShouldThrowNoAccountInsertedException() {
		accountDataStructure.setIban(null);
		accountDataStructure.setBalance(BigDecimal.valueOf(500));
		accountDataStructure.setCurrencyCode("JOD");
		accountDataStructure.setType("investment");
		accountDataStructure.setStatus(ACTIVE);
		accountsGateway.createAccount(accountDataStructure);
		assertEquals(accountDataStructure.getIban(),
				accountsGateway.loadAccountByIban("JO94CBJO0010000000000131000321").getIban());
	}

	@Test
	public void givenAccountsGateway_CallingLoadAccounts_ShouldReturnAccounts() {
		Iterable<AccountView> account = accountsGateway.loadAccounts();
		assertTrue(account.iterator().hasNext());
	}

}
