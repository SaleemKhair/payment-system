package com.progressoft.jip.usecases.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.beans.Currency;
import com.progressoft.jip.gateways.views.CurrencyView;
import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.usecases.CurrencyUseCases;

public class CurrencyUseCasesImpl implements CurrencyUseCases {

	private CurrencyRepository currencyRepository;

	public CurrencyUseCasesImpl(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	@Override
	public CurrencyView getCurrency(String currencyCode) {
		return currencyRepository.loadCurrencyByCode(currencyCode);
	}

	@Override
	public Collection<CurrencyView> getAllCurrencies() {
		Collection<Currency> loadCurrencies = currencyRepository.loadCurrencies();
		List<CurrencyView> currencies = new ArrayList<>();
		currencies.addAll(loadCurrencies);
		return currencies;
	}

}
