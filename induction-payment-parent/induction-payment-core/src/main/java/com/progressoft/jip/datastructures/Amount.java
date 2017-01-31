package com.progressoft.jip.datastructures;

import java.io.Serializable;
import java.math.BigDecimal;

import com.progressoft.jip.handlers.impl.CurrencyExchangeRateHandler;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;

public class Amount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal amountValue;
	private String currencyCode;
	private CurrencyExchangeRateRepository currencyExchangeRateRepository;

	public Amount(CurrencyExchangeRateRepository currenctExchangeRateRepository, BigDecimal amount, String currencyCode) {
		this.currencyExchangeRateRepository = currenctExchangeRateRepository;
		this.amountValue = amount;
		this.currencyCode = currencyCode;
	}

	public BigDecimal getAmount() {
		return amountValue;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal valueIn(String targetCurrencyCode) {
		CurrencyExchangeRateHandler loadCurrencyExchangeRate = currencyExchangeRateRepository
				.loadCurrencyExchangeRate(currencyCode, targetCurrencyCode);
		return loadCurrencyExchangeRate.convert(amountValue);
	}

}
