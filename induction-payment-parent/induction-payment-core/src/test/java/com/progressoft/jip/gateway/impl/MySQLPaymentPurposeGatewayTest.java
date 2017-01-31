package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.behaviors.impl.PaymentPurposeBehaviorsFactoryImpl;
import com.progressoft.jip.gateways.PaymentPurposeGateway;
import com.progressoft.jip.gateways.exceptions.DataExceedingCodeColumnLimitException;
import com.progressoft.jip.gateways.exceptions.DataExceedingNameColumnLimitException;
import com.progressoft.jip.gateways.exceptions.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.gateways.exceptions.EmptyPaymentPurposeCodeException;
import com.progressoft.jip.gateways.exceptions.NoneExistingPaymentPurposeException;
import com.progressoft.jip.gateways.exceptions.NullDataSourceException;
import com.progressoft.jip.gateways.exceptions.PaymentPurposeNotFoundException;
import com.progressoft.jip.gateways.sql.impl.MySqlPaymentPurposeGateway;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeCodeException;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeNameException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class MySQLPaymentPurposeGatewayTest {

	private PaymentPurposeGateway mySQLPaymentPurposeGateway;
	private BasicDataSource dataSource;

	@Before
	public void setup() {
		dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
		mySQLPaymentPurposeGateway = new MySqlPaymentPurposeGateway(dataSource,
				new PaymentPurposeBehaviorsFactoryImpl());
	}

	@Test(expected = NullDataSourceException.class)
	public void createPaymentPurposeGateway_PassingNullDataSource_ShouldThrowNullDataSource() {
		mySQLPaymentPurposeGateway = new MySqlPaymentPurposeGateway(null, new PaymentPurposeBehaviorsFactoryImpl());
	}

	@Test(expected = EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingEmptyCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("");
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode(null);
	}

	@Test
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingExistingCode_ShouldReturnPaymentPurpose() {
		assertEquals(mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("SALA").getCode(), "SALA");
	}

	@Test(expected = PaymentPurposeNotFoundException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingNoneExistingCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("ggg");
	}

	@Test(expected = DataExceedingCodeColumnLimitException.class)
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassPaymentPurposeExceedingPurposeCodeLengthLimit_ShouldThrowDataExceedingCodeColumnLimit() {
		PaymentPurpose paymentPurposeDataStructure = new PaymentPurpose("GHAD11111112241274", "GHADEER");
		mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Test(expected = DataExceedingNameColumnLimitException.class)
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassPaymentPurposeExceedingPurposeNameLengthLimit_ShouldThrowDataExceedingCodeColumnLimit() {
		PaymentPurpose paymentPurposeDataStructure = new PaymentPurpose("GHAD3",
				"GHADEER124GHADEER124GHADEER124GHADEER124452712");
		mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Test(expected = DuplicatePaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassPaymentPurposWithExistingCode_ShouldThrowDuplicatePaymentPurposeCode() {
		PaymentPurpose paymentPurposeDataStructure = new PaymentPurpose("SALA", "GHADEER");
		mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Test
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposes_ShouldRetunPaymentPurposes() {
		assertEquals(2, new ArrayList<PaymentPurposeView>(mySQLPaymentPurposeGateway.loadPaymentPurposes()).size());
	}

	@Test
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_ThenCallingDeletePurPose_ShouldInsertThePurposeThenDeleteIt() {
		mySQLPaymentPurposeGateway.insertPaymentPurpose(new PaymentPurpose("AHM", "AHMAD"));
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode("AHM");
	}

	@Test(expected = NoneExistingPaymentPurposeException.class)
	public void givenPaymentPurposeGateway_CallingDeletePaymentPurposeByCode_PassingNoneExistingCode_ShouldThrowNoneExistingPaymentPurpose() {
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode("CDFVD");
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingDeletePaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode(null);
	}

	@Test(expected = EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingDeletePaymentPurposeByCode_PassingEmptyCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode("");
	}

	@Test
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingExistingPurposeCodeWithNewPurposeName_ShouldUpdateName() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName(buildPaymentPurpose("GHAD", "saleem"));
		PaymentPurposeView paymentPurposeDataStructure = mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("GHAD");
		assertEquals("saleem", paymentPurposeDataStructure.getName());
	}

	@Test(expected = NoneExistingPaymentPurposeException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingNoneExistingPurposeCodeWithNewPurposeName_ShouldThrowNoneExistingPaymentPurpose() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName(buildPaymentPurpose("AMAR", "kamar"));
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingNullPurposeCodeWithNewPurposeName_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName(buildPaymentPurpose(null, "kamar"));
	}

	@Test(expected = EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingEmptyPurposeCodeWithNewPurposeName_ShouldThrowEmptyPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName(buildPaymentPurpose("", "rayeg"));
	}

	@Test(expected = NullPaymentPurposeNameException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingNullPurposeNameWithNewPurposeName_ShouldThrowNullPaymentPurposeName() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName(buildPaymentPurpose("null", null));
	}

	private PaymentPurpose buildPaymentPurpose(String code, String name) {
		return new PaymentPurpose(code, name);
	}
}
