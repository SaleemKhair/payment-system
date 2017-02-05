package com.progressoft.jip.jparepositories.impl;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.jparepositories.exceptions.NullCurrencyCodeException;

public class CurrencyJpaRepositoryImplTest {
	private static final String CURRENCYCODE = "JOD";
	private static final String PERSISTENCE_UNIT_NAME = "induction-payment-jpa";

	private CurrencyJpaRepositoryImpl currencyJpaRepository;

	@Before
	public void setUp() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,
				prepareDBProperties());
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		currencyJpaRepository = new CurrencyJpaRepositoryImpl(entityManager);
	}

	@Test
	public void givenCurrencyJpaRepository_WhenCallingloadCurrencies_willReturnListOfCurrencies() {
		Collection<CurrencyEntity> loadedCurrencies = currencyJpaRepository.loadCurrencies();
		assertTrue(loadedCurrencies.iterator().hasNext());
	}

	@Test
	public void givenCurrencyJpaRepository_WhenCallingloadCurrencyByCode_ThenPassingCurrencyCode_itWillReturnCurrency() {
		CurrencyEntity loadCurrencyByCode = currencyJpaRepository.loadCurrencyByCode(CURRENCYCODE);
		Assert.assertEquals(loadCurrencyByCode.getCode(), CURRENCYCODE);
	}

	@Test(expected = NullCurrencyCodeException.class)
	public void givenCurrencyJpaRepository_WhenCallingLoadCurrencyByCode_ThenPassingEmptyCode_ShouldThrowNullCurrencyCodeException() {
		currencyJpaRepository.loadCurrencyByCode("");
	}

	@Test(expected = NullCurrencyCodeException.class)
	public void givenCurrencyJpaRepository_WhenCallingLoadCurrencyByCode_ThenPassingNullCode_ShouldThrowNullCurrencyCodeException() {
		currencyJpaRepository.loadCurrencyByCode(null);
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
