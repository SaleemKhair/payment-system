package com.progressoft.jip.jparepositories.impl;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.progressoft.jip.entities.AccountEntity;
import com.progressoft.jip.jparepositories.AbstractRepository;
import com.progressoft.jip.jparepositories.AccountJpaRepository;
import com.progressoft.jip.jparepositories.exceptions.AccountAlreadyExistsException;
import com.progressoft.jip.jparepositories.exceptions.NoAccountUpdatedException;
import com.progressoft.jip.jparepositories.exceptions.NullAccountIBANException;

import repositories.loader.EntityManagerLoader;

public class AccountJpaRepositoryImpl extends AbstractRepository implements AccountJpaRepository {

	private static final String FIND_ALL_ACCOUNT = "AccountEntity.findAll";
	private static final String FIND_ACCOUNT_BY_IBAN = "AccountEntity.findByIban";

	public AccountJpaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public AccountJpaRepositoryImpl() {
		super(EntityManagerLoader.getEntityManger());
	}
		@Override
	public AccountEntity loadAccountByIban(String iban) {
		if (Objects.isNull(iban) || "".equals(iban))
			throw new NullAccountIBANException();
		return transactObject(entityManager -> {
			Query createNamedQuery = entityManager.createNamedQuery(FIND_ACCOUNT_BY_IBAN);
			createNamedQuery.setParameter("iban", iban);
			return createNamedQuery.getSingleResult();
		});
	}

	@Override
	public Collection<AccountEntity> loadAccounts() {
		return transactObject(entityManager -> {
			TypedQuery<AccountEntity> typedQuery = entityManager.createNamedQuery(FIND_ALL_ACCOUNT,
					AccountEntity.class);
			return typedQuery.getResultList();
		});
	}

	@Override
	public void updateAccount(AccountEntity account) {
		transactObject(entityManager -> {
			AccountEntity accountEntityByIban = entityManager.find(AccountEntity.class, account.getIban());
			if (Objects.isNull(accountEntityByIban))
				throw new NoAccountUpdatedException();
			return entityManager.merge(account);
		});
	}

	@Override
	public void createAccount(AccountEntity newAccount) {
		transactObject(entityManager -> {
			AccountEntity accountEntity = entityManager.find(AccountEntity.class, newAccount.getIban());
			if (Objects.isNull(accountEntity)) {
				entityManager.persist(newAccount);
				return Void.getVoid();
			}
			throw new AccountAlreadyExistsException();
		});
	}
}
