package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.progressoft.jip.beans.Currency;
import com.progressoft.jip.gateways.CurrencyGateway;
import com.progressoft.jip.repository.CurrencyRepository;

public class CurrencyRepositoryImpl implements CurrencyRepository {

	private CurrencyGateway currencyGateway;

	public CurrencyRepositoryImpl(CurrencyGateway currencyGateway) {
		this.currencyGateway = currencyGateway;
	}

	@Override
	public Collection<Currency> loadCurrencies() {
		ArrayList<Currency> collection = new ArrayList<>();
		currencyGateway.loadCurrencies().stream().forEach(c -> collection.add((Currency) c));
		return Collections.unmodifiableCollection(collection);
	}

	@Override
	public Currency loadCurrencyByCode(String currencyCode) {
		return (Currency) currencyGateway.loadCurrencyByCode(currencyCode);
	}
}
