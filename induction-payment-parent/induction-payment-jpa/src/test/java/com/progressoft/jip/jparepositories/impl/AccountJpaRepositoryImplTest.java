package com.progressoft.jip.jparepositories.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.entities.AccountEntity;
import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.jparepositories.exceptions.AccountAlreadyExistsException;
import com.progressoft.jip.jparepositories.exceptions.NoAccountUpdatedException;
import com.progressoft.jip.jparepositories.exceptions.NullAccountIBANException;

public class AccountJpaRepositoryImplTest {

	private static final String NEW_ACCOUNT_IBAN = "RO49AAAA1B31245007593840000577555";
	private static final String PERSISTENCE_UNIT_NAME = "induction-payment-jpa";
	private static final String AVAILABLE_ACCOUNT_IBAN = "AZ21NABZ00000000137010001944";
	private static final String UNAVAILABLE_ACCOUNT_IBAN = "AZ21NABZ0sss0000000137010001944";

	private AccountJpaRepositoryImpl accountJpaRepository;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, prepareDBProperties());
		entityManager = entityManagerFactory.createEntityManager();
		accountJpaRepository = new AccountJpaRepositoryImpl(entityManager);
	}

	@After
	public void clearData() {
		entityManager.getTransaction().begin();
		AccountEntity find = entityManager.find(AccountEntity.class, NEW_ACCOUNT_IBAN);
		if (Objects.nonNull(find))
			entityManager.remove(find);
		entityManager.getTransaction().commit();
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenAccountRepositrey_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
		accountJpaRepository.loadAccountByIban(null);
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenAccountRepositrey_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
		accountJpaRepository.loadAccountByIban("");
	}

	@Test
	public void givenAccountJpaRepositry_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
		AccountEntity loadAccountByIban = accountJpaRepository.loadAccountByIban(AVAILABLE_ACCOUNT_IBAN);
		Assert.assertEquals(loadAccountByIban.getIban(), AVAILABLE_ACCOUNT_IBAN);

	}

	@Test
	public void givenAccountJpaRepositry_CallingUpdateAcount_PassingExistingAccount_ThenCallingLoadAccountByIBAN_ShouldReturnUpdatedAccount() {
		AccountEntity account = accountJpaRepository.loadAccountByIban(AVAILABLE_ACCOUNT_IBAN);
		CurrencyEntity currency = new CurrencyEntity();
		currency.setCode("USD");
		account.setCurrency(currency);
		accountJpaRepository.updateAccount(account);
		Assert.assertEquals("USD",
				accountJpaRepository.loadAccountByIban(AVAILABLE_ACCOUNT_IBAN).getCurrency().getCode());
	}

	@Test
	public void givenAccountJpaRepositry_CallingloadAccounts_ShouldReturnAccountsCollecton() {
		Collection<AccountEntity> loadAccounts = accountJpaRepository.loadAccounts();
		AccountEntity entity = loadAccounts.iterator().next();
		Assert.assertNotNull(entity);
	}

	@Test(expected = NoAccountUpdatedException.class)
	public void givenAccountJpaRepository_CallingUpdateAccount_ShouldThrowNoAccountHasBeenUpdated() {
		AccountEntity account = new AccountEntity();
		CurrencyEntity currency = new CurrencyEntity();
		account.setCurrency(currency);
		account.setIban(UNAVAILABLE_ACCOUNT_IBAN);
		accountJpaRepository.updateAccount(account);
	}

	@Test
	public void givenAccountJpaRepositry_CallingCreateAccount_ShouldCreateAccount() {
		AccountEntity account = populateNewAccount();
		accountJpaRepository.createAccount(account);
		assertEquals(account.getIban(), NEW_ACCOUNT_IBAN);
	}

	@Test(expected = AccountAlreadyExistsException.class)
	public void givenAccountGateway_CallingCreateAccountWithPredifnedAccount_ShouldThrowAccountIsAlreadyExistException() {
		AccountEntity accountByIBAN = accountJpaRepository.loadAccountByIban(AVAILABLE_ACCOUNT_IBAN);
		accountJpaRepository.createAccount(accountByIBAN);
	}

	private AccountEntity populateNewAccount() {
		AccountEntity insertAccount = new AccountEntity();
		CurrencyEntity currency = new CurrencyEntity();
		currency.setCode("JOD");
		insertAccount.setIban(NEW_ACCOUNT_IBAN);
		insertAccount.setBalance(new BigDecimal(500));
		insertAccount.setCurrency(currency);
		insertAccount.setType("investment");
		insertAccount.setStatus("ACTIVE");
		insertAccount.setRule("THIS_MONTH");
		return insertAccount;
	}

	private Map<String, String> prepareDBProperties() {
		Map<String, String> settingsMap = new HashMap<>();
		settingsMap.put("javax.persistence.jdbc.user", "root");
		settingsMap.put("javax.persistence.jdbc.password", "root");
		settingsMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mockdata");
		settingsMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
		settingsMap.put("hibernate.hbm2ddl.auto", "update");
		return settingsMap;
	}

}
