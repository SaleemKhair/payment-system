package com.progressoft.jip.jobwatcher.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.gateway.sql.YahooCurrencyExchangeRateGateway;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.PaymentRequestGateway;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.PaymentRequestHandler;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.impl.AccountHandlerImpl;
import com.progressoft.jip.handlers.impl.PaymentRequestHandlerImpl;
import com.progressoft.jip.handlers.validators.impl.PaymentRequestValidator;
import com.progressoft.jip.jobwatcher.JobWatcher;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.impl.AccountRepositoryImpl;
import com.progressoft.jip.repository.impl.CurrencyExchangeRateRepositoryImpl;
import com.progressoft.jip.repository.impl.PaymentPurposeRepositoryImpl;
import com.progressoft.jip.repository.impl.PaymentRequestRepositoryImpl;
import com.progressoft.jip.usecases.PaymentPurposeGatewayMock;
import com.progressoft.jip.usecases.validators.impl.AccountValidator;
import com.progressoft.jip.utilities.restful.RestfulResponseFormat;
import com.progressoft.jip.utilities.restful.impl.YahooCurrenciesXmlResponseParser;

public class JobWatcherTest {

	private static JobWatcher jobWatcher;
	private static PaymentRequestRepository paymentRequestRepository;
	private static AccountRepository accountRepository;
	private static AccountHandler accountHandler;

	@Before
	public void setUp() {
		AccountGateway accountGateway = new AccountGatewayMock();
		accountRepository = new AccountRepositoryImpl(accountGateway);

		PaymentRequestGateway paymentRequestGateway = new PaymentRequestGatewayMock();
		paymentRequestRepository = new PaymentRequestRepositoryImpl(paymentRequestGateway);

		CurrencyExchangeRateRepository currencyExchangeRateRepository = new CurrencyExchangeRateRepositoryImpl(
				new YahooCurrencyExchangeRateGateway(RestfulResponseFormat.XML,
						new YahooCurrenciesXmlResponseParser()));
		PaymentRequestHandler paymentHandler = new PaymentRequestHandlerImpl(currencyExchangeRateRepository,
				new PaymentRequestValidator(accountRepository,
						new PaymentPurposeRepositoryImpl(new PaymentPurposeGatewayMock())));
		accountHandler = new AccountHandlerImpl(new AccountValidator());

		jobWatcher = new JobWatcher(paymentHandler, accountRepository, paymentRequestRepository, accountHandler);
		jobWatcher.startCronJobSchedule(3000);
	}

	@After
	public void tearDown() {
		jobWatcher.shutDown();
	}

	@Test
	public void insertPaymentRequest() throws ValidationException, InterruptedException {
		paymentRequestRepository.insertPaymentRequest(
				buildPaymentDataStructure(LocalDate.now(), "JO94CBJO0010000000000131000302", "AZ21NA37010001944"));
		Thread.sleep(4000);
		assertEquals(500.0,
				accountRepository.loadAccountByIban("JO94CBJO0010000000000131000302").getBalance().doubleValue(), 0);
	}

	@Test
	public void insertMultiplePaymentRequestsDifferentDates() throws ValidationException, InterruptedException {
		paymentRequestRepository.insertPaymentRequest(
				buildPaymentDataStructure(LocalDate.now(), "AD1200012030200359100100", "AZ21NA37010001944"));
		paymentRequestRepository.insertPaymentRequest(
				buildPaymentDataStructure(LocalDate.now().plusDays(1), "BE62510007547061", "AT611904300234573201"));

		Thread.sleep(4000);
		assertEquals(500.0, accountRepository.loadAccountByIban("AD1200012030200359100100").getBalance().doubleValue(),
				0);
		assertEquals(1000.0, accountRepository.loadAccountByIban("BE62510007547061").getBalance().doubleValue(), 0);

	}
	
	@Test
	public void insertMultiplePaymentRequests() throws ValidationException, InterruptedException {
		paymentRequestRepository.insertPaymentRequest(
				buildPaymentDataStructure(LocalDate.now(), "AD1200012030200359100100", "AZ21NA37010001944"));
		Thread.sleep(4000);
		assertEquals(500.0, accountRepository.loadAccountByIban("AD1200012030200359100100").getBalance().doubleValue(),
				0);
		paymentRequestRepository.insertPaymentRequest(
				buildPaymentDataStructure(LocalDate.now(), "BE62510007547061", "AT611904300234573201"));
		Thread.sleep(4000);
		assertEquals(500.0, accountRepository.loadAccountByIban("BE62510007547061").getBalance().doubleValue(),
				0);
		paymentRequestRepository.insertPaymentRequest(
				buildPaymentDataStructure(LocalDate.now(), "JO94CBJO0010000000000131000302", "AT611904300234573201"));
		Thread.sleep(4000);
		assertEquals(500.0, accountRepository.loadAccountByIban("JO94CBJO0010000000000131000302").getBalance().doubleValue(),
				0);
	

	}

	private PaymentRequest buildPaymentDataStructure(LocalDate paymentDate, String orderingIban, String benefIban) {
		PaymentRequest req = new PaymentRequest();
		req.setBeneficiaryAccountIban(benefIban);
		req.setOrderingAccountIban(orderingIban);
		req.setBeneficiaryName("Never");
		req.setCurrencyCode("USD");
		req.setPaymentAmount(new BigDecimal("500"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(paymentDate));
		return req;
	}
	


}
