package com.progressoft.jip.usecases;

import java.util.Collection;

import com.progressoft.jip.gateways.views.CurrencyView;

public interface CurrencyUseCases {

	CurrencyView getCurrency(String currencyCode);

	Collection<CurrencyView> getAllCurrencies();

}
