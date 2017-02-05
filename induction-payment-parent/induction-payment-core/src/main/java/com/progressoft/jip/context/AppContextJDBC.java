package com.progressoft.jip.context;

import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.behaviors.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.behaviors.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.behaviors.PaymentPurposeBehaviorsFactory;
import com.progressoft.jip.behaviors.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.behaviors.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.behaviors.impl.CurrencyGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.behaviors.impl.PaymentPurposeBehaviorsFactoryImpl;
import com.progressoft.jip.behaviors.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.sql.YahooCurrencyExchangeRateGateway;
import com.progressoft.jip.gateways.sql.impl.MySqlAccountGateway;
import com.progressoft.jip.gateways.sql.impl.MySqlCurrencyGateway;
import com.progressoft.jip.gateways.sql.impl.MySqlPaymentPurposeGateway;
import com.progressoft.jip.gateways.sql.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.PaymentPurposeHandler;
import com.progressoft.jip.handlers.PaymentRequestHandler;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.impl.AccountHandlerImpl;
import com.progressoft.jip.handlers.impl.PaymentPurposeHandlerImpl;
import com.progressoft.jip.handlers.impl.PaymentRequestHandlerImpl;
import com.progressoft.jip.handlers.validators.Validator;
import com.progressoft.jip.handlers.validators.impl.AccountValidator;
import com.progressoft.jip.handlers.validators.impl.PaymentPurposeValidator;
import com.progressoft.jip.handlers.validators.impl.PaymentRequestValidator;
import com.progressoft.jip.iban.IBANGeneralValidator;
import com.progressoft.jip.jobwatcher.JobWatcher;
import com.progressoft.jip.report.ReportProvider;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.impl.AccountRepositoryImpl;
import com.progressoft.jip.repository.impl.CurrencyExchangeRateRepositoryImpl;
import com.progressoft.jip.repository.impl.CurrencyRepositoryImpl;
import com.progressoft.jip.repository.impl.PaymentPurposeRepositoryImpl;
import com.progressoft.jip.repository.impl.PaymentRequestRepositoryImpl;
import com.progressoft.jip.rules.impl.FiveDaysAheadRule;
import com.progressoft.jip.rules.impl.FiveMonthsAheadRule;
import com.progressoft.jip.rules.impl.FiveYearsAheadRule;
import com.progressoft.jip.rules.impl.PaymentRules;
import com.progressoft.jip.usecases.AccountUseCases;
import com.progressoft.jip.usecases.CurrencyUseCases;
import com.progressoft.jip.usecases.PaymentPurposeUseCases;
import com.progressoft.jip.usecases.PaymentRequestUseCases;
import com.progressoft.jip.usecases.impl.AccountUseCasesImpl;
import com.progressoft.jip.usecases.impl.CurrencyUseCasesImpl;
import com.progressoft.jip.usecases.impl.PaymentPurposeUseCasesImpl;
import com.progressoft.jip.usecases.impl.PaymentRequestUseCasesImpl;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;
import com.progressoft.jip.utilities.chequewriting.impl.EnglishChequeAmountWriter;
import com.progressoft.jip.utilities.restful.RestfulResponseFormat;
import com.progressoft.jip.utilities.restful.impl.YahooCurrenciesXmlResponseParser;

public class AppContextJDBC implements AppContext {

	private static PaymentRules rules = PaymentRules.getInstance();
	private static AbstractAmountWriter amountWriter = new AbstractAmountWriter();

	private static PaymentRequestBehaviorsFactory paymentRequestBehaviorsFactory = new PaymentRequestBehaviorsFactoryImpl();
	private static PaymentPurposeBehaviorsFactory paymentPurposeBehaviorsFactory = new PaymentPurposeBehaviorsFactoryImpl();
	private static CurrencyGatewayDBBehaviorsFactory currencyBehaviorsFactory = new CurrencyGatewayDBBehaviorsFactoryImpl();
	private static AccountGatewayDBBehaviorsFactory accountBehaviorsFactory = new AccountGatewayDBBehaviorsFactoryImpl();

	private final IBANGeneralValidator ibanGeneralValidator;
	private final Validator<Account, ValidationException> accountValidator;
	private final Validator<PaymentPurpose, PurposeValidationException> paymentPurposeValidator;
	private final Validator<PaymentRequest, ValidationException> paymentRequestValidator;

	private final AccountHandler accountHandler;
	private final PaymentPurposeHandler paymentPurposeHandler;
	private final PaymentRequestHandler paymentRequestHandler;

	private final PaymentRequestRepository paymentRequestRepository;
	private final PaymentPurposeRepository paymentPurposeRepository;
	private final CurrencyRepository currencyRepository;
	private final AccountRepository accountRepository;
	private final CurrencyExchangeRateRepository currenctExchangeRateRepository;

	private final AccountUseCases accountUseCases;
	private final CurrencyUseCases currencyUseCases;
	private final PaymentPurposeUseCases paymentPurposeUseCases;
	private final PaymentRequestUseCases paymentRequestUseCases;
	private JobWatcher jobWatcher;
	private static AppContextJDBC instance;

	private AppContextJDBC(DataSource dataSource) {
		rules.registerRule("five.years.ahead", new FiveYearsAheadRule());
		rules.registerRule("five.months.ahead", new FiveMonthsAheadRule());
		rules.registerRule("five.days.ahead", new FiveDaysAheadRule());

		amountWriter.addWriter("EnglishWriter", new EnglishChequeAmountWriter());

		paymentPurposeRepository = new PaymentPurposeRepositoryImpl(
				new MySqlPaymentPurposeGateway(dataSource, paymentPurposeBehaviorsFactory));
		currencyRepository = new CurrencyRepositoryImpl(new MySqlCurrencyGateway(dataSource, currencyBehaviorsFactory));
		accountRepository = new AccountRepositoryImpl(new MySqlAccountGateway(dataSource, accountBehaviorsFactory));

		paymentRequestRepository = new PaymentRequestRepositoryImpl(
				new MySqlPaymentRequestGateway(dataSource, paymentRequestBehaviorsFactory));
		currenctExchangeRateRepository = new CurrencyExchangeRateRepositoryImpl(new YahooCurrencyExchangeRateGateway(
				RestfulResponseFormat.XML, new YahooCurrenciesXmlResponseParser()));

		ibanGeneralValidator = new IBANGeneralValidator();
		accountValidator = new AccountValidator(ibanGeneralValidator);
		paymentPurposeValidator = new PaymentPurposeValidator();
		paymentRequestValidator = new PaymentRequestValidator(accountRepository, paymentPurposeRepository);

		accountHandler = new AccountHandlerImpl(accountValidator);
		paymentPurposeHandler = new PaymentPurposeHandlerImpl(paymentPurposeValidator);
		paymentRequestHandler = new PaymentRequestHandlerImpl(currenctExchangeRateRepository, paymentRequestValidator);

		accountUseCases = new AccountUseCasesImpl(accountRepository, accountHandler);
		currencyUseCases = new CurrencyUseCasesImpl(currencyRepository);
		paymentPurposeUseCases = new PaymentPurposeUseCasesImpl(paymentPurposeRepository, paymentPurposeHandler);
		paymentRequestUseCases = new PaymentRequestUseCasesImpl(paymentRequestHandler, accountRepository,
				paymentRequestRepository, new ReportProvider(paymentRequestRepository));
		jobWatcher = new JobWatcher(paymentRequestHandler, accountRepository, paymentRequestRepository, accountHandler);
		jobWatcher.startCronJobSchedule();
	}

	public static AppContextJDBC getContext(DataSource dataSource) {
		synchronized (AppContextJDBC.class) {
			if (Objects.isNull(instance)) {
				instance = new AppContextJDBC(dataSource);
			}
			return instance;
		}
	}

	@Override
	public PaymentRequestUseCases getPaymentRequestUseCases() {
		return paymentRequestUseCases;
	}

	@Override
	public PaymentPurposeUseCases getPaymentPurposeUseCases() {
		return paymentPurposeUseCases;
	}

	@Override
	public CurrencyUseCases getCurrencyUseCases() {
		return currencyUseCases;
	}

	@Override
	public AccountUseCases getAccountUseCases() {
		return accountUseCases;
	}

	@Override
	public List<String> getPaymentRuleNames() {
		return rules.getRuleNames();
	}

	@Override
	public AbstractAmountWriter getAbstractAmountWriter() {
		return amountWriter;
	}

}
