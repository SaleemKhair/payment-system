package com.progressoft.jip.gateway;

import com.progressoft.jip.datastructures.CurrencyExchangeRate;

public interface CurrencyExchangeRateGateway {

	CurrencyExchangeRate loadCurrencyExchangeRate(String codeFrom, String codeTo);

}