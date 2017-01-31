package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.behaviors.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateways.exceptions.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateways.sql.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.handlers.exceptions.AccountRuleViolationException;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.iban.exception.InvalidIBANException;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.exceptions.RepositoryException;
import com.progressoft.jip.rules.impl.FiveDaysAheadRule;
import com.progressoft.jip.rules.impl.FiveMonthsAheadRule;
import com.progressoft.jip.rules.impl.FiveYearsAheadRule;
import com.progressoft.jip.rules.impl.PaymentRules;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.utilities.Utilities;
import com.progressoft.jip.utilities.chequewriting.ChequeAmountWriter;
import com.progressoft.jip.utilities.chequewriting.impl.EnglishChequeAmountWriter;

public class PaymentRequestRepositoryTest {

	private PaymentRules rules = PaymentRules.getInstance();
	private BasicDataSource dataSource;
	private PaymentRequestRepository paymentRequestRepository;

	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom1','ibanto1','bname',555.5,'JOD','SALA','2013-09-04','');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04','');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom2','ibanto2','bname',555.5,'JOD','SALA','2013-09-04','');";

	@Before
	public void setUp() {
		initRules();
		initDataSource();
		initPaymentRepository();
		fillPaymentRequestTable(dataSource);
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

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingExistingId_ShouldReturnThePaymentRequestModel() {
		PaymentRequest paymentRequest = paymentRequestRepository.loadPaymentRequestById(1);
		assertEquals(1, paymentRequest.getId());

	}

	@Test(expected = NoneExistingPaymentRequestException.class)
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingNoneExistingId_ShouldThrowNoneExistingPaymentRequest() {
		paymentRequestRepository.loadPaymentRequestById(0);
	}

	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingModel_ShouldInsertTheModel()
			throws ValidationException {
		PaymentRequest req = buildPaymentDataStructure(LocalDate.of(2017, 1, 10), "AZ21NABZ00000000137010001944");
		paymentRequestRepository.insertPaymentRequest(req);
		PaymentRequest loaded = paymentRequestRepository.loadPaymentRequestById(4);
		assertTrue(loaded.equals(loadPayment()));
	}

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequests_ShouldLoadAllPaymentRequests() {
		assertEquals(3, paymentRequestRepository.loadPaymentRequests().size());
	}

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingExistingIBAN_ShouldReturnTwoRequests()
			throws RepositoryException {
		assertEquals(2, paymentRequestRepository.loadPaymentRequestsByOrderingAccountIban("ibanFrom1").size());
	}

	@Test
	public void givenPaymentRequestRepository_CallingDeletePaymentRequestById_ThenCallingPaymentPurposes_ShouldReternOne() {
		paymentRequestRepository.deletePaymentRequestById(1);
		assertEquals(2, paymentRequestRepository.loadPaymentRequests().size());
	}

	@Ignore
	// TODO to useCases Test
	@Test(expected = AccountRuleViolationException.class)
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingExistingAccountWithViolatedRule_ShouldThrowAccountRuleViolationException()
			throws ValidationException {

		PaymentRequest req = buildPaymentDataStructure(LocalDate.of(1994, 5, 10), "AZ21NABZ00000000137010001944");
		paymentRequestRepository.insertPaymentRequest(req);

	}

	@Ignore
	// TODO to useCases Tests
	@Test(expected = AccountRuleViolationException.class)
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingPastDay_ShouldThrowAccountRuleViolationException()
			throws ValidationException {
		PaymentRequest req = buildPaymentDataStructure(LocalDate.of(2025, 1, 5), "AZ21NABZ00000000137010001944");
		paymentRequestRepository.insertPaymentRequest(req);

	}

	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PaymentAmountShouldBeChequeWritten()
			throws ValidationException {
		PaymentRequest req = buildPaymentDataStructure(LocalDate.of(2017, 1, 10), "AZ21NABZ00000000137010001944");
		ChequeAmountWriter chequeAmountWriter = new EnglishChequeAmountWriter();
		req.setAmountInWords(chequeAmountWriter.writeAmountInWords(req.getPaymentAmount(), req.getCurrencyCode()));
		paymentRequestRepository.insertPaymentRequest(req);
		assertTrue(loadPayment().getAmountInWords().length() > 0);
	}

	private PaymentRequest loadPayment() {
		PaymentRequest loadedPayment = (PaymentRequest) paymentRequestRepository.loadPaymentRequestById(4);
		return loadedPayment;
	}

	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingExistingAccountWithRule_ShouldBeInserted()
			throws ValidationException {
		PaymentRequest req = buildPaymentDataStructure(LocalDate.now(), "AZ21NABZ00000000137010001944");
		paymentRequestRepository.insertPaymentRequest(req);
		PaymentRequest loaded = paymentRequestRepository.loadPaymentRequestById(4);
		assertTrue(loaded.equals(loadPayment()));

	}

	@Ignore
	// TODO to useCases test
	@Test(expected = InvalidIBANException.class)
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingInvalidIban_ShouldThrowInvalidIBANException()
			throws ValidationException {
		PaymentRequest req = buildPaymentDataStructure(LocalDate.now(), "AZ21NA37010001944");
		paymentRequestRepository.insertPaymentRequest(req);

	}

	private PaymentRequest buildPaymentDataStructure(LocalDate paymentDate, String benefIban) {
		PaymentRequest req = new PaymentRequest();
		req.setBeneficiaryAccountIban(benefIban);
		req.setOrderingAccountIban("JO94CBJO0010000000000131000302");
		req.setBeneficiaryName("Never");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(paymentDate));
		return req;
	}

	private void initRules() {
		rules.registerRule("five.years.ahead", new FiveYearsAheadRule());
		rules.registerRule("five.months.ahead", new FiveMonthsAheadRule());
		rules.registerRule("five.days.ahead", new FiveDaysAheadRule());
	}

	private void initDataSource() {
		dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
	}

	private void initPaymentRepository() {
		paymentRequestRepository = new PaymentRequestRepositoryImpl(
				new MySqlPaymentRequestGateway(dataSource, new PaymentRequestBehaviorsFactoryImpl()));
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

}
