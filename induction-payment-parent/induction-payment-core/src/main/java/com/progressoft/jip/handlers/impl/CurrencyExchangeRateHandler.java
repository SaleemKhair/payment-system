package com.progressoft.jip.handlers.impl;

import java.math.BigDecimal;

import com.progressoft.jip.datastructures.CurrencyExchangeRate;

public class CurrencyExchangeRateHandler {

	private CurrencyExchangeRate currencyExchangeRateDataStructure;

	public CurrencyExchangeRateHandler(CurrencyExchangeRate currencyExchangeRateDataStructure) {
		this.currencyExchangeRateDataStructure = currencyExchangeRateDataStructure;
	}

	public BigDecimal convert(BigDecimal amount) {
		return amount.multiply(new BigDecimal(String.valueOf(currencyExchangeRateDataStructure.getRate())));
	}

}
