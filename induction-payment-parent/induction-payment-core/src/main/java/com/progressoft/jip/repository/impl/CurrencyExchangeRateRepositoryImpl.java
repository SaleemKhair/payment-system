package com.progressoft.jip.repository.impl;

import java.util.Objects;

import com.progressoft.jip.gateway.CurrencyExchangeRateGateway;
import com.progressoft.jip.gateways.exceptions.NullGatewayException;
import com.progressoft.jip.handlers.impl.CurrencyExchangeRateHandler;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;

public class CurrencyExchangeRateRepositoryImpl implements CurrencyExchangeRateRepository {

	private CurrencyExchangeRateGateway gateway;

	public CurrencyExchangeRateRepositoryImpl(CurrencyExchangeRateGateway gateway) {
		if (Objects.isNull(gateway))
			throw new NullGatewayException();
		this.gateway = gateway;
	}

	@Override
	public CurrencyExchangeRateHandler loadCurrencyExchangeRate(String codeFrom, String codeTo) {
		return new CurrencyExchangeRateHandler(gateway.loadCurrencyExchangeRate(codeFrom, codeTo));
	}

}
