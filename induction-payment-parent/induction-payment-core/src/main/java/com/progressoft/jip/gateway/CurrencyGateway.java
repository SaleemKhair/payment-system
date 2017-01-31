package com.progressoft.jip.gateway;

import java.util.Collection;

import com.progressoft.jip.gateways.views.CurrencyView;

public interface CurrencyGateway {

    Collection<CurrencyView> loadCurrencies();

    CurrencyView loadCurrencyByCode(String currencyCode);

}
