package com.progressoft.jip.behaviors.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.behaviors.AbstractBehavior;
import com.progressoft.jip.behaviors.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.gateways.exceptions.AccountNotFoundException;
import com.progressoft.jip.gateways.exceptions.InvalidBalanceException;
import com.progressoft.jip.gateways.exceptions.NoAccountInsertedException;
import com.progressoft.jip.gateways.exceptions.NoAccountUpdatedException;
import com.progressoft.jip.gateways.exceptions.NullAccountIBANException;
import com.progressoft.jip.gateways.views.AccountView;

public class AccountGatewayDBBehaviorsFactoryImpl implements AccountGatewayDBBehaviorsFactory {

	public static final Behavior<AccountView> LOAD_ACCOUNT_BY_IBAN = new AbstractBehavior<AccountView>() {

		@Override
		public AccountView operation() {
			String iban = (String) parameters[0];
			if (Objects.isNull(iban) || iban.trim().length() == 0) {
				throw new NullAccountIBANException();
			}
			try {
				List<Account> list = runner.query(Constants.GET_ACCOUNT_BY_IBAN, new BeanListHandler<>(Account.class),
						iban);
				if (list.isEmpty())
					throw new AccountNotFoundException();
				return list.get(0);
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};

	public static final Behavior<Collection<AccountView>> LOAD_ACCOUNTS = new AbstractBehavior<Collection<AccountView>>() {

		@Override
		public Collection<AccountView> operation() {
			try {
				ArrayList<AccountView> collection = new ArrayList<>();
				runner.query(Constants.GET_ACCOUNTS, new BeanListHandler<>(Account.class)).stream()
						.forEach(a -> collection.add((AccountView) a));
				return collection;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};
	public static final Behavior<Void> INSERT_ACCOUNT_BEHAVIOR = new AbstractBehavior<Void>() {
		int effectedRow = 0;

		@Override
		public Void operation() {
			AccountView dataStructure = (AccountView) parameters[0];
			if (dataStructure.getBalance().doubleValue() < 0)
				throw new InvalidBalanceException();
			try {
				effectedRow = runner.update(Constants.INSERT_ACCOUNT, dataStructure.getIban(), dataStructure.getType(),
						dataStructure.getBalance(), dataStructure.getStatus(), dataStructure.getCurrencyCode(),
						dataStructure.getRule());
			} catch (SQLException e) {
				throw new NoAccountInsertedException(e);
			}
			if (effectedRow == 0)
				throw new NoAccountInsertedException();
			return null;
		}
	};
	private static final Behavior<Void> UPDATE_ACCOUNT_BEHAVIOR = new AbstractBehavior<Void>() {
		int effectedRow = 0;

		@Override
		public Void operation() {
			AccountView dataStructure = (AccountView) parameters[0];
			if (dataStructure.getBalance().doubleValue() < 0)
				throw new InvalidBalanceException();
			try {
				effectedRow = runner.update(Constants.UPDATE_ACCOUNT, dataStructure.getType(),
						dataStructure.getBalance(), dataStructure.getStatus(), dataStructure.getCurrencyCode(),
						dataStructure.getRule(), dataStructure.getIban());
			} catch (SQLException e) {
				throw new NoAccountUpdatedException(e);
			}
			if (effectedRow == 0)
				throw new NoAccountUpdatedException();
			return null;
		}
	};

	@Override
	public Behavior<AccountView> loadAccountByIBAN() {
		return LOAD_ACCOUNT_BY_IBAN;
	}

	@Override
	public Behavior<Collection<AccountView>> loadAccounts() {
		return LOAD_ACCOUNTS;
	}

	@Override
	public Behavior<Void> createAccount() {
		return INSERT_ACCOUNT_BEHAVIOR;
	}

	@Override
	public Behavior<Void> updateAccount() {
		return UPDATE_ACCOUNT_BEHAVIOR;
	}

}
