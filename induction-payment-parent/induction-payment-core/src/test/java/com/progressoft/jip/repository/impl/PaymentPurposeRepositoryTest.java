package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.behaviors.impl.PaymentPurposeBehaviorsFactoryImpl;
import com.progressoft.jip.gateways.exceptions.NoneExistingPaymentPurposeException;
import com.progressoft.jip.gateways.sql.impl.MySqlPaymentPurposeGateway;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeCodeException;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeNameException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class PaymentPurposeRepositoryTest {

	private PaymentPurposeRepositoryMock paymentPurposeRepository;

	@Before
	public void setup() {
		BasicDataSource dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
		paymentPurposeRepository = new PaymentPurposeRepositoryMock(
				new MySqlPaymentPurposeGateway(dataSource, new PaymentPurposeBehaviorsFactoryImpl()));
	}

	@Test
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingExistingCode_ShouldReturnThePaymentPurpose() {
		assertEquals("GHAD", paymentPurposeRepository.loadPaymentPurposeByCode("GHAD").getCode());
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeRepository.loadPaymentPurposeByCode(null);
	}

	@Test
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposes_ShouldReturnAllPaymentPurposes() {
		assertEquals(2, paymentPurposeRepository.loadPaymentPurposes().size());
	}

	@Test
	public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingExistingPaymentPurposeCode_ThenCallingLoadPaymentPurpose_ShouldUpdatePaymentPurposeName() {
		paymentPurposeRepository.updatePaymenPurposeName(buildPaymentPurpose("GHAD", "new name"));
		assertEquals("new name", paymentPurposeRepository.loadPaymentPurposeByCode("GHAD").getName());
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingNullPaymentPurposeCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeRepository.updatePaymenPurposeName(buildPaymentPurpose(null, "kamar"));
	}

	

	@Test(expected = NullPaymentPurposeNameException.class)
	public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingNullPaymentPurposeName_ShouldThrowNullPaymentPurposeName() {
		paymentPurposeRepository.updatePaymenPurposeName(buildPaymentPurpose("null", null));
	}

	@Test
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_ShouldInsertPaymentPurpose() {
		paymentPurposeRepository.insertPaymentPurpose("NEW1", "newName");
		paymentPurposeRepository.deletePaymentPurpose("NEW1");
	}

	@Test(expected = NoneExistingPaymentPurposeException.class)
	public void givenPaymentPurposeRepository_CallingDeletePaymentPurpose_PassingNoneExistingPaymentPurposeCode_ShouldThrowNoneExistingPaymentPurpose() {
		paymentPurposeRepository.deletePaymentPurpose("jjjj");
	}

	private PaymentPurpose buildPaymentPurpose(String code, String name) {
		return new PaymentPurpose(code, name);
	}

}
