package com.progressoft.jip.gateways.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.gateways.CurrencyGateway;
import com.progressoft.jip.gateways.jpa.converters.EntityToBeanConverter;
import com.progressoft.jip.gateways.views.CurrencyView;
import com.progressoft.jip.jparepositories.CurrencyJpaRepository;

public class CurrencyJpaGateway implements CurrencyGateway {
	private EntityToBeanConverter beanConverter;
	private CurrencyJpaRepository currencyJpaRepository;

	public CurrencyJpaGateway(EntityToBeanConverter beanConverter, CurrencyJpaRepository currencyJpaRepository) {
		this.beanConverter = beanConverter;
		this.currencyJpaRepository = currencyJpaRepository;
	}

	@Override
	public Collection<CurrencyView> loadCurrencies() {
		Collection<CurrencyEntity> loadCurrencies = currencyJpaRepository.loadCurrencies();
		List<CurrencyView> currencyViews = new ArrayList<>();
		loadCurrencies.stream().forEach(c -> currencyViews.add(beanConverter.toCurrencyBean(c)));
		return currencyViews;
	}

	@Override
	public CurrencyView loadCurrencyByCode(String currencyCode) {
		CurrencyEntity loadCurrencyByCode = currencyJpaRepository.loadCurrencyByCode(currencyCode);
		return beanConverter.toCurrencyBean(loadCurrencyByCode);
	}

}
