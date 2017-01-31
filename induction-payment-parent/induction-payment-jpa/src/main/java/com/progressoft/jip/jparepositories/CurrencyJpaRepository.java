package com.progressoft.jip.jparepositories;

import java.util.Collection;

import com.progressoft.jip.entities.CurrencyEntity;

public interface CurrencyJpaRepository {

    Collection<CurrencyEntity> loadCurrencies();

    CurrencyEntity loadCurrencyByCode(String currencyCode);

}
