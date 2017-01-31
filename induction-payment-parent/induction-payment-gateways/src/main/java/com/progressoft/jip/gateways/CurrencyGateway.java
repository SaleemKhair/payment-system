package com.progressoft.jip.gateways;

import java.util.Collection;

import com.progressoft.jip.gateways.views.CurrencyView;

public interface CurrencyGateway {

    Collection<CurrencyView> loadCurrencies();

    CurrencyView loadCurrencyByCode(String currencyCode);

}
