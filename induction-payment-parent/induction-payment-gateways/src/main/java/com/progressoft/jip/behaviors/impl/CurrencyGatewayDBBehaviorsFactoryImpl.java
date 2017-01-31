package com.progressoft.jip.behaviors.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.beans.Currency;
import com.progressoft.jip.behaviors.AbstractBehavior;
import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.behaviors.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.gateways.exceptions.CurrencyNotFoundExption;
import com.progressoft.jip.gateways.exceptions.ShortCurrencyCodeException;
import com.progressoft.jip.gateways.views.CurrencyView;

public class CurrencyGatewayDBBehaviorsFactoryImpl implements CurrencyGatewayDBBehaviorsFactory {

	@Override
	public Behavior<Collection<CurrencyView>> loadCurrencies() {
		return LOAD_CURRENCIES;
	}

	@Override
	public Behavior<CurrencyView> loadCurrencyByCode() {
		return LOAD_CURRENCY_BY_CODE;
	}

	public static final Behavior<Collection<CurrencyView>> LOAD_CURRENCIES = new AbstractBehavior<Collection<CurrencyView>>() {

		@Override
		public Collection<CurrencyView> operation() {
			try {
				ArrayList<CurrencyView> collection = new ArrayList<>();
				runner.query(Constants.SELECT_ALL_CRNCYS, new BeanListHandler<>(Currency.class)).stream()
						.forEach(c -> collection.add((CurrencyView) c));
				return collection;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};

	public static final Behavior<CurrencyView> LOAD_CURRENCY_BY_CODE = new AbstractBehavior<CurrencyView>() {

		@Override
		public CurrencyView operation() {
			String code = (String) parameters[0];
			if (isShortCode(code))
				throw new ShortCurrencyCodeException();
			return loadCurrency(loadCurrenciesFromDB(code));
		}

		private CurrencyView loadCurrency(CurrencyView currency) {
			if (Objects.isNull(currency))
				throw new CurrencyNotFoundExption();
			return currency;
		}

		private CurrencyView loadCurrenciesFromDB(String code) {
			try {
				return runner.query(Constants.SELECT_CRNCY_BY_CODE, new BeanHandler<>(Currency.class), code);
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

		private boolean isShortCode(String code) {
			return code.length() < 3;
		}
	};

}
