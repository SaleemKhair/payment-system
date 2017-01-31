package com.progressoft.jip.jparepositories;

import javax.persistence.EntityManager;
@FunctionalInterface
public interface TransactionHandler {
	Object doTransaction(EntityManager entityManager);
}
