package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.gateway.sql.YahooCurrencyExchangeRateGateway;
import com.progressoft.jip.gateways.exceptions.NullGatewayException;
import com.progressoft.jip.handlers.impl.CurrencyExchangeRateHandler;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.utilities.restful.RestfulResponseFormat;
import com.progressoft.jip.utilities.restful.impl.YahooCurrenciesXmlResponseParser;

public class CurrencyExchangeRateRepositoryImplTest {

	private CurrencyExchangeRateRepository repository;

	@Before
	public void createCurrenctExchangeRateRepository() {
		repository = new CurrencyExchangeRateRepositoryImpl(new YahooCurrencyExchangeRateGateway(
				RestfulResponseFormat.XML, new YahooCurrenciesXmlResponseParser()));
	}

	@Test(expected = NullGatewayException.class)
	public void createCurrenctExchangeRateRepository_PassingNullGateway_ShouldThrowNullGateway() {
		new CurrencyExchangeRateRepositoryImpl(null);
	}

	@Test
	public void givenCurrencyExchangeRateRepository_CallingloadCurrencyExchangeRate_PassingValidCurrencyCodes() {
		CurrencyExchangeRateHandler currencyExchangeRate = repository.loadCurrencyExchangeRate("JOD", "USD");
		assertNotNull(currencyExchangeRate);
	}

}
