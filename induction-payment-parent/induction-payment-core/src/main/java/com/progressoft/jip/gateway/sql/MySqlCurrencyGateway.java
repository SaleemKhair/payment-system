package com.progressoft.jip.gateway.sql;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.behaviors.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.gateway.AbstractGateway;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.gateways.views.CurrencyView;



public class MySqlCurrencyGateway extends AbstractGateway implements CurrencyGateway {

    private Behavior<Collection<CurrencyView>> loadCurrencies;
    private Behavior<CurrencyView> loadCurrencyByCode;

    public MySqlCurrencyGateway(DataSource dataSource, CurrencyGatewayDBBehaviorsFactory factory) {
	super(dataSource);
	this.loadCurrencies = factory.loadCurrencies();
	this.loadCurrencyByCode = factory.loadCurrencyByCode();
    }

    @Override
    public Collection<CurrencyView> loadCurrencies() {
	return loadCurrencies.execute(dataSource);
    }

    public CurrencyView loadCurrencyByCode(String currencyCode) {
	return loadCurrencyByCode.execute(dataSource, currencyCode);
    }
}
