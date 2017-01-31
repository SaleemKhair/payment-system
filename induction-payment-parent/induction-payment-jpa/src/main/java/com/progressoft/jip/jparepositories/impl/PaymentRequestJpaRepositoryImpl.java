package com.progressoft.jip.jparepositories.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.progressoft.jip.entities.PaymentRequestEntity;
import com.progressoft.jip.jparepositories.AbstractRepository;
import com.progressoft.jip.jparepositories.PaymentRequestJpaRepository;
import com.progressoft.jip.jparepositories.exceptions.DuplicatePaymentRequestException;
import com.progressoft.jip.jparepositories.exceptions.InvalidPaymentRequestDateException;
import com.progressoft.jip.jparepositories.exceptions.InvalidPaymentRequestIbanException;
import com.progressoft.jip.jparepositories.exceptions.NoPaymentRequestDeletedException;
import com.progressoft.jip.jparepositories.exceptions.NoPaymentRequestFoundException;
import com.progressoft.jip.jparepositories.exceptions.NoPaymentRequestUpdatedException;
import com.progressoft.jip.jparepositories.exceptions.NullPaymentRequestException;
import com.progressoft.jip.jparepositories.exceptions.NullPaymentRequestIDException;

import repositories.loader.EntityManagerLoader;

public class PaymentRequestJpaRepositoryImpl extends AbstractRepository implements PaymentRequestJpaRepository {

	public PaymentRequestJpaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public PaymentRequestJpaRepositoryImpl() {
		super(EntityManagerLoader.getEntityManger());
	}

	@Override
	public void deletePaymentRequestById(int id) {

		if (Objects.isNull(id))
			throw new NullPaymentRequestIDException();

		transactObject(e -> {
			PaymentRequestEntity find = e.find(PaymentRequestEntity.class, id);
			if (Objects.isNull(find))
				throw new NoPaymentRequestDeletedException();
			e.remove(find);
			return Void.getVoid();
		});

	}

	@Override
	public PaymentRequestEntity loadPaymentRequestById(int id) {

		if (Objects.isNull(id)) {
			throw new NullPaymentRequestIDException();
		}
		return transactObject(e -> {
			PaymentRequestEntity find = e.find(PaymentRequestEntity.class, id);
			if (Objects.isNull(find))
				throw new NoPaymentRequestFoundException();
			return find;
		});
	}

	@Override
	public void insertPaymentRequest(PaymentRequestEntity paymentRequest) {
		if (Objects.isNull(paymentRequest))
			throw new NullPaymentRequestException();
		transactObject(e -> {
			PaymentRequestEntity find = e.find(PaymentRequestEntity.class, paymentRequest.getId());
			if (Objects.nonNull(find))
				throw new DuplicatePaymentRequestException();
			e.persist(paymentRequest);
			return Void.getVoid();
		});
	}

	@Override
	public Collection<PaymentRequestEntity> loadPaymentRequests() {
		return transactObject(e -> {
			TypedQuery<PaymentRequestEntity> query = e.createNamedQuery("PaymentRequestEntity.findAll",
					PaymentRequestEntity.class);
			return query.getResultList();
		});
	}

	@Override
	public Collection<PaymentRequestEntity> loadPaymentRequestByPaymentDate(Date paymentRequestDate) {
		if (Objects.isNull(paymentRequestDate))
			throw new InvalidPaymentRequestDateException();
		return transactObject(e -> {
			TypedQuery<PaymentRequestEntity> query = e.createNamedQuery("PaymentRequestEntity.findByDate",
					PaymentRequestEntity.class);
			query.setParameter("paymentDate", paymentRequestDate);
			return query.getResultList();
		});
	}

	@Override
	public Collection<PaymentRequestEntity> loadPaymentRequestsByOrderingAccountIBAN(String iban) {
		if (Objects.isNull(iban))
			throw new InvalidPaymentRequestIbanException();

		return transactObject(e -> {
			TypedQuery<PaymentRequestEntity> query = e.createNamedQuery("PaymentRequest.findByOrderingIBAN",
					PaymentRequestEntity.class);
			query.setParameter("iban", iban);
			return query.getResultList();
		});
	}

	@Override
	public void updatePaymentRequest(PaymentRequestEntity paymentRequest) {
		if (Objects.isNull(paymentRequest)) {
			throw new NoPaymentRequestFoundException();
		}
		transactObject(entityManager -> {
			PaymentRequestEntity find = entityManager.find(PaymentRequestEntity.class, paymentRequest.getId());
			if (Objects.isNull(find)) {
				throw new NoPaymentRequestUpdatedException();
			}
			entityManager.merge(paymentRequest);
			return Void.getVoid();
		});
	}

}
