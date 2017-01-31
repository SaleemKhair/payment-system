package com.progressoft.jip.gateways.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.entities.AccountEntity;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.jpa.converters.BeanToEntityConverter;
import com.progressoft.jip.gateways.jpa.converters.EntityToBeanConverter;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.jparepositories.AccountJpaRepository;

public class AccountJpaGateway implements AccountGateway {
	private AccountJpaRepository accountJpaRepository;
	private EntityToBeanConverter beanConverter;
	private BeanToEntityConverter entityConverter;

	public AccountJpaGateway(EntityToBeanConverter beanConverter, BeanToEntityConverter entityConverter,
			AccountJpaRepository accountJpaRepository) {
		this.accountJpaRepository = accountJpaRepository;
		this.beanConverter = beanConverter;
		this.entityConverter = entityConverter;
	}

	@Override
	public AccountView loadAccountByIban(String iban) {
		AccountEntity accountByIBAN = accountJpaRepository.loadAccountByIban(iban);
		return beanConverter.toAccountBean(accountByIBAN);
	}

	@Override
	public Collection<AccountView> loadAccounts() {
		Collection<AccountEntity> loadAccounts = accountJpaRepository.loadAccounts();
		List<AccountView> accountViews = new ArrayList<>();
		loadAccounts.stream().forEach(a -> accountViews.add(beanConverter.toAccountBean(a)));
		return accountViews;
	}

	@Override
	public void updateAccount(AccountView account) {
		AccountEntity accountEntity = entityConverter.toAccountEntity(account);
		accountJpaRepository.updateAccount(accountEntity);
	}

	@Override
	public void createAccount(AccountView newAccount) {
		AccountEntity accountEntity = entityConverter.toAccountEntity(newAccount);
		accountJpaRepository.createAccount(accountEntity);
	}

}
