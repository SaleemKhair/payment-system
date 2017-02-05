package com.progressoft.jip.jparepositories.impl;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.entities.PaymentPurposeEntity;
import com.progressoft.jip.jparepositories.exceptions.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.jparepositories.exceptions.NoPaymentPurposeFoundException;
import com.progressoft.jip.jparepositories.exceptions.PaymentPurposeCodeRequiredException;

public class PaymentPurposeJpaRepositoryImplTest {

	private static final String PERSISTENCE_UNIT_NAME = "induction-payment-jpa";
	private PaymentPurposeJpaRepositoryImpl paymentPurposeJpaRepository;

	@Before
	public void setUp() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,
				prepareDBProperties());
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		paymentPurposeJpaRepository = new PaymentPurposeJpaRepositoryImpl(entityManager);
	}

	@Test(expected = PaymentPurposeCodeRequiredException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingEmptyCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeJpaRepository.loadPaymentPurposeByCode("");
	}

	@Test(expected = PaymentPurposeCodeRequiredException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeJpaRepository.loadPaymentPurposeByCode(null);
	}

	@Test
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingExistingCode_ShouldReturnPaymentPurpose() {
		assertEquals(paymentPurposeJpaRepository.loadPaymentPurposeByCode("GHAD").getCode(), "GHAD");
	}

	@Test(expected = NoPaymentPurposeFoundException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingNoneExistingCode_ShowThrowPaymentPurposeNotFoundException() {
		paymentPurposeJpaRepository.loadPaymentPurposeByCode("q");
	}

	@Test
	public void givenPaymentPurposRepository_CallingInsertPaymentPurpose_ShouldBeenInsertedPaymentPurpose() {
		PaymentPurposeEntity paymentPurpose = new PaymentPurposeEntity();
		paymentPurpose.setCode("TREA");
		paymentPurpose.setName("TreasuryPayment");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurpose);
		Assert.assertEquals(paymentPurpose.getCode(), "TREA");
		paymentPurposeJpaRepository.deletePaymentPurposeByCode(paymentPurpose.getCode());
	}

	@Test(expected = DuplicatePaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepositorey_CallingInsertPaymentPurpose_PassingExistingPaymentPurpose_ShouThrowPaymentPurposeIsAlreadyExistException() {
		PaymentPurposeEntity paymentPurpose = paymentPurposeJpaRepository.loadPaymentPurposeByCode("GHAD");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurpose);
	}

	@Test
	public void givenPaymentPurposeRepositorey_CallingLoadPaymentPurposes_ShouldRetunPaymentPurposes() {
		Collection<PaymentPurposeEntity> paymentPurposes = paymentPurposeJpaRepository.loadPaymentPurposes();
		PaymentPurposeEntity entity = paymentPurposes.iterator().next();

		Assert.assertNotNull(entity);
	}

	@Test(expected = NoPaymentPurposeFoundException.class)
	public void givenPaymentPurposeRepositorey_CallingInsertPaymentPurpose_ThenCallingDeletePurpose_ShouldInsertThePurposeThenDeleteIt() {
		PaymentPurposeEntity paymentPurpose = new PaymentPurposeEntity();
		paymentPurpose.setCode("KHwA");
		paymentPurpose.setName("Abusair");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurpose);
		paymentPurposeJpaRepository.deletePaymentPurposeByCode("KHwA");
		paymentPurposeJpaRepository.loadPaymentPurposeByCode(paymentPurpose.getCode());

	}

	@Test(expected = PaymentPurposeCodeRequiredException.class)
	public void givenPaymentPurposeRepositorey_CallingDeletePaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeJpaRepository.deletePaymentPurposeByCode(null);
	}

	@Test(expected = PaymentPurposeCodeRequiredException.class)
	public void givenPaymentPurposeRepositorey_CallingDeletePaymentPurposeByCode_PassingEmptyCode_ShouldThrowEmptyPaymentPurposeCode() {
		paymentPurposeJpaRepository.deletePaymentPurposeByCode("");
	}

	@Test
	public void givenPaymentPurposeRepositorey_CallingUpdatePaymentPurpose_PassingExistingPurposeCodeWithNewPurposeName_ShouldUpdateName() {
		PaymentPurposeEntity loadPaymentPurposeByCode = paymentPurposeJpaRepository.loadPaymentPurposeByCode("GHAD");
		loadPaymentPurposeByCode.setName("Sameeeeeeeeeeeeer");
		paymentPurposeJpaRepository.updatePaymentPurposeName(loadPaymentPurposeByCode);
		Assert.assertEquals("Sameeeeeeeeeeeeer",
				paymentPurposeJpaRepository.loadPaymentPurposeByCode("GHAD").getName());
	}

	private Map<String, String> prepareDBProperties() {
		Map<String, String> settingsMap = new HashMap<>();
		settingsMap.put("javax.persistence.jdbc.user", "root");
		settingsMap.put("javax.persistence.jdbc.password", "root");
		settingsMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mockdata");
		settingsMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
		settingsMap.put("hibernate.hbm2ddl.auto", "update");
		return settingsMap;
	}

}
