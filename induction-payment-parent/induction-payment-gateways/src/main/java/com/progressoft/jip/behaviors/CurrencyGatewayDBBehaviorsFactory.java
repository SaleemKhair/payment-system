package com.progressoft.jip.behaviors;

import java.util.Collection;

import com.progressoft.jip.gateways.views.CurrencyView;

public interface CurrencyGatewayDBBehaviorsFactory {
    
    Behavior<Collection<CurrencyView>> loadCurrencies();

    Behavior<CurrencyView> loadCurrencyByCode();

}
