package com.progressoft.jip.jparepositories;

import javax.persistence.EntityManager;

public class AbstractRepository {

	private EntityManager entityManager;

	public AbstractRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	protected <T> T transactObject(TransactionHandler transactionHandler) {
		boolean isCommitted = false;
		try {
			entityManager.getTransaction().begin();
			Object obj = transactionHandler.doTransaction(entityManager);
			entityManager.getTransaction().commit();
			isCommitted = true;
			return (T) obj;
		} finally {
			if (!isCommitted) {
				entityManager.getTransaction().rollback();
			}
		}
	}
}
