package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.behaviors.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.exceptions.AccountNotFoundException;
import com.progressoft.jip.gateways.exceptions.InvalidBalanceException;
import com.progressoft.jip.gateways.exceptions.NullAccountIBANException;
import com.progressoft.jip.gateways.sql.impl.MySqlAccountGateway;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.utilities.DataBaseSettings;

public class AccountRepositoryTest {

	private static final String IBAN = "JO94CBJO0010000000000131000302";
	private AccountRepository accountRepository;

	@Before
	public void setup() {
		BasicDataSource dataSource = connectionConfiguration();
		AccountGateway accountGateway = new MySqlAccountGateway(dataSource, new AccountGatewayDBBehaviorsFactoryImpl());
		accountRepository = new AccountRepositoryImpl(accountGateway);
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
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
		accountRepository.loadAccountByIban(null);
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
		accountRepository.loadAccountByIban("");
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingUnavailableIBANCode_ShouldThrowNullAccountIBAN() {
		accountRepository.loadAccountByIban("ekjsfnefk");
	}

	@Test
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
		Account account = accountRepository.loadAccountByIban(IBAN);
		assertEquals(IBAN, account.getIban());
	}

	@Test
	public void givenAccountRepository_CallingUpdateAcount_PassingExistingAccount_ThenCallingLoadAccountByIBAN_ShouldReturnUpdatedAccount() {
		Account originalAccount = new Account();
		originalAccount.setIban(IBAN);
		originalAccount.setType("TYPE");
		double newBalance = Math.random() * 500;
		originalAccount.setBalance(BigDecimal.valueOf(newBalance));
		originalAccount.setStatus("ACTIVE");
		originalAccount.setRule("five.months.ahead");
		accountRepository.updateAccount(originalAccount);
		Account updatedAccount = accountRepository.loadAccountByIban(IBAN);
		AccountView view = updatedAccount;
		assertTrue(Math.abs(view.getBalance().doubleValue() - originalAccount.getBalance().doubleValue()) <= 1e-3);
	}

	@Test(expected = InvalidBalanceException.class)
	public void givenMySQLAccountGateway_CallingUpdateAcount_PassingNigativeBalance_ShouldThrowInvalidBalance() {
		Account originalAccount = new Account();
		originalAccount.setIban(IBAN);
		originalAccount.setType("TYPE");
		originalAccount.setBalance(BigDecimal.valueOf(-200));
		originalAccount.setStatus("ACTIVE");
		accountRepository.updateAccount(originalAccount);
	}

	@Test
	public void givenAccountsRepository_CallingLoadAccounts_ShouldReturnAccounts() {
		Iterable<Account> account = accountRepository.loadAccounts();
		assertTrue(account.iterator().hasNext());
	}

}
