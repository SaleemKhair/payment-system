package com.progressoft.jip.repository;

import com.progressoft.jip.handlers.impl.CurrencyExchangeRateHandler;

@FunctionalInterface
public interface CurrencyExchangeRateRepository {

	CurrencyExchangeRateHandler loadCurrencyExchangeRate(String codeFrom, String codeTo);

}