package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.behaviors.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateways.PaymentRequestGateway;
import com.progressoft.jip.gateways.exceptions.EmptyAccountIBANException;
import com.progressoft.jip.gateways.exceptions.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateways.exceptions.NullAccountIBANException;
import com.progressoft.jip.gateways.exceptions.NullDataSourceException;
import com.progressoft.jip.gateways.sql.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.rules.impl.FiveDaysAheadRule;
import com.progressoft.jip.rules.impl.FiveMonthsAheadRule;
import com.progressoft.jip.rules.impl.FiveYearsAheadRule;
import com.progressoft.jip.rules.impl.PaymentRules;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.utilities.Utilities;

public class MySQLPaymentRequestGatewayTest {

	private PaymentRequestGateway paymentRequestGatway;
	private BasicDataSource dataSource;
	private PaymentRules rules;

	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanFrom','ibanto1','bname',555.5,'JOD','SALA','2013-09-04','');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanFrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04','');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanFrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04','');";

	@Before
	public void createMySQLPymentRequestGatway() {
		prepareRules();
		prepareDataSource();
		initPaymentRequestRepository();
	}

	private void initPaymentRequestRepository() {
		paymentRequestGatway = new MySqlPaymentRequestGateway(dataSource, new PaymentRequestBehaviorsFactoryImpl());
		fillPaymentRequestTable(dataSource);
	}

	private void prepareDataSource() {
		dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
	}

	private void prepareRules() {
		rules = PaymentRules.getInstance();
		rules.registerRule("five.years.ahead", new FiveYearsAheadRule());
		rules.registerRule("five.months.ahead", new FiveMonthsAheadRule());
		rules.registerRule("five.days.ahead", new FiveDaysAheadRule());
	}

	private void fillPaymentRequestTable(BasicDataSource dataSource) {
		try {
			Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1)
					.executeUpdate();
			Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2)
					.executeUpdate();
			Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3)
					.executeUpdate();

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@After
	public void after() {
		try {
			Utilities.preparedStatement(dataSource.getConnection(), Constants.TRUNCATE_PAYMENT_REQUEST_TABLE)
					.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Test(expected = NullDataSourceException.class)
	public void creatingPaymentRequestGateway_PassingNullDataSource_ShouldThrowNullDataSource() {
		new MySqlPaymentRequestGateway(null, new PaymentRequestBehaviorsFactoryImpl());
	}

	@Test
	public void givenPaymentRequestGateway_CallingLoadPaymentRequestById_PassingExistingID_ShouldReturnThePaymentRequest() {
		assertEquals(1, paymentRequestGatway.loadPaymentRequestById(1).getId());
	}

	@Test(expected = NoneExistingPaymentRequestException.class)
	public void givenPaymentRequestGateway_CallingLoadPaymentRequestById_PassingNoneExistingID_ShouldThrowNoneExistingPaymentRequest() {
		paymentRequestGatway.loadPaymentRequestById(10).getId();
	}

	@Test(expected = NoneExistingPaymentRequestException.class)
	public void givenMySQLPaymentRequestGateway_CallingDeletePaymentRequestById_PassingNonExistingPaymentRequest_ShouldThrowNoneExistingPaymentRequst() {
		paymentRequestGatway.deletePaymentRequestById(4);
	}

	@Test
	public void givenPaymentRequestGateway_CallingInsertPaymentRequest_ThenCallingLoadPaymentRequestById_ShouldReturnThePaymentRequest() {
		PaymentRequest req = newPaymentRequest();
		paymentRequestGatway.insertPaymentRequest(req);
		paymentRequestGatway.loadPaymentRequestById(3);
	}

	private PaymentRequest newPaymentRequest() {
		PaymentRequest req = new PaymentRequest();
		req.setBeneficiaryAccountIban("ibanTo");
		req.setOrderingAccountIban("ibanFrom");
		req.setBeneficiaryName("whatever");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(2017, 1, 2)));
		return req;
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingNullIBAN_ShouldThrowNullAccountIBAN() {
		assertEquals(2, paymentRequestGatway.loadPaymentRequestsByOrderingAccountIban(null).size());
	}

	@Test(expected = EmptyAccountIBANException.class)
	public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingEmptyIBAN_ShouldThrowEmptyAccountIBAN() {
		assertEquals(2, paymentRequestGatway.loadPaymentRequestsByOrderingAccountIban("").size());
	}

	@Test
	public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingExistingIBAN_ShouldReturnTwoRequests() {
		assertEquals(2, paymentRequestGatway.loadPaymentRequestsByOrderingAccountIban("ibanFrom1").size());
	}

	@Test
	public void givenPaymentRequestGateway_CallingLoadPaymentRequests_ShouldReturnAllPaymentRequests() {
		assertEquals(3, paymentRequestGatway.loadPaymentRequests().size());
	}

	@Test
	public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByPaymentDate_ShouldReturnAllPaymentsDueThatDay() {
		assertEquals(3, paymentRequestGatway.loadPaymentRequestsByPaymentDate(LocalDate.of(2013, 9, 4)).size());
	}

}
