package com.progressoft.jip.jparepositories.impl;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.progressoft.jip.entities.PaymentPurposeEntity;
import com.progressoft.jip.jparepositories.AbstractRepository;
import com.progressoft.jip.jparepositories.PaymentPurposeJpaRepository;
import com.progressoft.jip.jparepositories.exceptions.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.jparepositories.exceptions.NoPaymentPurposeFoundException;
import com.progressoft.jip.jparepositories.exceptions.NoPaymentPurposeUpdatedException;
import com.progressoft.jip.jparepositories.exceptions.NullPaymentPurposeException;
import com.progressoft.jip.jparepositories.exceptions.PaymentPurposeCodeRequiredException;

import repositories.loader.EntityManagerLoader;

public class PaymentPurposeJpaRepositoryImpl extends AbstractRepository implements PaymentPurposeJpaRepository {
	private static final String FIND_ALL_PAYMENT_PURPOSES = "PaymentPurposeEntity.findAll";

	public PaymentPurposeJpaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public PaymentPurposeJpaRepositoryImpl() {
		super(EntityManagerLoader.getEntityManger());
	}

	@Override
	public PaymentPurposeEntity loadPaymentPurposeByCode(String code) {
		if (Objects.isNull(code) || "".equals(code)) {
			throw new PaymentPurposeCodeRequiredException();
		}
		return transactObject(entityManager -> {
			PaymentPurposeEntity entity = entityManager.find(PaymentPurposeEntity.class, code);
			if (Objects.isNull(entity))
				throw new NoPaymentPurposeFoundException();
			return entity;
		});
	}

	@Override
	public void insertPaymentPurpose(PaymentPurposeEntity paymentPurpose) {
		if (Objects.isNull(paymentPurpose)) {
			throw new NullPaymentPurposeException();
		}
		transactObject(entityManager -> {
			PaymentPurposeEntity find = entityManager.find(PaymentPurposeEntity.class, paymentPurpose.getCode());
			if (Objects.nonNull(find)) {
				throw new DuplicatePaymentPurposeCodeException();
			}
			entityManager.persist(paymentPurpose);
			return Void.getVoid();
		});
	}

	@Override
	public Collection<PaymentPurposeEntity> loadPaymentPurposes() {
		return transactObject(entityManager -> {
			TypedQuery<PaymentPurposeEntity> typedQuery = entityManager.createNamedQuery(FIND_ALL_PAYMENT_PURPOSES,
					PaymentPurposeEntity.class);
			return typedQuery.getResultList();
		});
	}

	@Override
	public void deletePaymentPurposeByCode(String code) {
		if (Objects.isNull(code) || "".equals(code)) {
			throw new PaymentPurposeCodeRequiredException();
		}
		transactObject(entityManager -> {
			PaymentPurposeEntity entity = entityManager.find(PaymentPurposeEntity.class, code);
			if (Objects.isNull(entity)) {
				throw new NoPaymentPurposeFoundException();
			}
			entityManager.remove(entity);
			return Void.getVoid();
		});
	}

	@Override
	public void updatePaymentPurposeName(PaymentPurposeEntity paymentPurpose) {
		transactObject(entityManager -> {
			PaymentPurposeEntity find = entityManager.find(PaymentPurposeEntity.class, paymentPurpose.getCode());
			if (Objects.isNull(find)) {
				throw new NoPaymentPurposeUpdatedException();
			}
			entityManager.merge(paymentPurpose);
			return Void.getVoid();
		});
	}

}
