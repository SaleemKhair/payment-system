package com.progressoft.jip.jparepositories.impl;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.jparepositories.AbstractRepository;
import com.progressoft.jip.jparepositories.CurrencyJpaRepository;
import com.progressoft.jip.jparepositories.exceptions.NullCurrencyCodeException;

import repositories.loader.EntityManagerLoader;

public class CurrencyJpaRepositoryImpl extends AbstractRepository implements CurrencyJpaRepository {

	private static final String FIND_CURRENCY_BY_CODE = "CurrencyEntity.findByCode";
	private static final String FIND_ALL_CURRENCIES = "CurrencyEntity.findAll";

	public CurrencyJpaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public CurrencyJpaRepositoryImpl() {
		super(EntityManagerLoader.getEntityManger());
	}

	@Override
	public Collection<CurrencyEntity> loadCurrencies() {
		return transactObject(entityManager -> {
			TypedQuery<CurrencyEntity> typedQuery = entityManager.createNamedQuery(FIND_ALL_CURRENCIES,
					CurrencyEntity.class);
			return typedQuery.getResultList();
		});
	}

	@Override
	public CurrencyEntity loadCurrencyByCode(String currencyCode) {
		if (Objects.isNull(currencyCode) || "".equals(currencyCode))
			throw new NullCurrencyCodeException();
		return transactObject(entityManager -> {
			Query createNamedQuery = entityManager.createNamedQuery(FIND_CURRENCY_BY_CODE);
			createNamedQuery.setParameter("code", currencyCode);
			return createNamedQuery.getSingleResult();
		});
	}

}
