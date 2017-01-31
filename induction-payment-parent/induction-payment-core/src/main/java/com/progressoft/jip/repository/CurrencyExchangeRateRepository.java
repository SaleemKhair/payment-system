package com.progressoft.jip.repository;

import com.progressoft.jip.handlers.impl.CurrencyExchangeRateHandler;

public interface CurrencyExchangeRateRepository {

	CurrencyExchangeRateHandler loadCurrencyExchangeRate(String codeFrom, String codeTo);

}