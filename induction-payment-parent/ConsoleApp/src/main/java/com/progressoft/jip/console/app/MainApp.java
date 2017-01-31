package com.progressoft.jip.console.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbcp.BasicDataSource;

import com.progressoft.jip.console.controller.ConsoleController;
import com.progressoft.jip.console.controller.ConsoleQuestionaty;
import com.progressoft.jip.console.controller.Logging;
import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextImpl;
import com.progressoft.jip.datastructures.PaymentRequest;
import com.progressoft.jip.handlers.exceptions.AccountRuleViolationException;
import com.progressoft.jip.menu.ConsoleMenu;
import com.progressoft.jip.menu.ConsoleOption;
import com.progressoft.jip.menu.action.Action;
import com.progressoft.jip.usecase.CreateNewPaymentRequestUseCase;
import com.progressoft.jip.usecase.CreateReportUseCase;
import com.progressoft.jip.usecase.LoadAccountsUseCase;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.utilities.chequewriting.impl.EnglishChequeAmountWriter;

public class MainApp {

	private static AppContext context = prepareContext();
	private static ConsoleController consoleController = new ConsoleController();
	private static ConsoleMenu loginMenu = new ConsoleMenu("Welcome to JIP Payment System");
	private static ConsoleMenu paymentMenu = new ConsoleMenu("Payment System");
	private static ConsoleMenu accountsMenu;
	private static ConsoleMenu reportMenu = new ConsoleMenu("R	eport Format");
	private static ConsoleMenu mainMenu = new ConsoleMenu("Main Menu");
	private static ConsoleQuestionaty loginQuestionary = new ConsoleQuestionaty();
	private static ConsoleQuestionaty paymentQuestionary = new ConsoleQuestionaty();
	private static final String BENEF_IBAN_Q = "Input benificary IBAN : ";
	private static final String AMNT_Q = "Input Amount :";
	private static final String CRNCY_Q = "Input Currency : ";
	private static final String DATE_Q = "Input Payment Date (day/month/year) : ";
	private static final String BENEF_NAME = "Input benificary name : ";
	private static final String PURPS_Q = "Input Payment Purpose CODE : ";
	private static final String USR_Q = "Input username :";
	private static final String PASS_Q = "Input password : ";
	private static CreateReportUseCase createReportUseCase;
	private static String orderingIban;
	private static Logging logger;

	private static final Action XML_ACTION = () -> {
		createReportUseCase = new CreateReportUseCase(context.paymentRequestRepositroy());
		createReportUseCase.generateXmlRepotForAccount(orderingIban);
	};

	private static final Action CSV_ACTION = () -> {
		createReportUseCase = new CreateReportUseCase(context.paymentRequestRepositroy());
		createReportUseCase.generateCsvReportForAccount(orderingIban);
	};

	private MainApp() {
	}

	public static void main(String[] args) {

		logger = Logging.getLoggerInstance(MainApp.class);
		ConsoleOption csvOption = new ConsoleOption("Print in CSV Format", 1,
				() -> prepateAndDisplayReportAccountMenu(CSV_ACTION));
		ConsoleOption xmlOption = new ConsoleOption("Print in XML Format", 2,
				() -> prepateAndDisplayReportAccountMenu(XML_ACTION));

		reportMenu.addOption(csvOption);
		reportMenu.addOption(xmlOption);

		ConsoleOption loadAccountsOption = new ConsoleOption("Load Accounts", 1,
				MainApp::prepareAndDisplayPaymentAccountsMenu);
		paymentMenu.addOption(loadAccountsOption);

		ConsoleOption submitPayment = new ConsoleOption("Submit payment", 1,
				() -> registerAndDisplayMenu(paymentMenu, "payment.menu"));

		ConsoleOption showPayments = new ConsoleOption("Print Payment Requests", 2,
				() -> registerAndDisplayMenu(reportMenu, "report.format.menu"));
		mainMenu.addOption(submitPayment);
		mainMenu.addOption(showPayments);

		ConsoleOption loginOption = new ConsoleOption("Login", 1, () -> {
			startLoginQuestionary();
			validateLoginAuth();
			registerAndDisplayMenu(mainMenu, "main.menu");

		});

		loginMenu.addOption(loginOption);
		registerAndDisplayMenu(loginMenu, "login.menu");

	}

	private static void prepateAndDisplayReportAccountMenu(Action action) {
		prepareAccountMenu(action);
		registerAndDisplayMenu(accountsMenu, "report.account.menu");
	}

	private static void prepareAndDisplayPaymentAccountsMenu() {
		prepareAccountMenu(() -> {
			startPaymentQuestionary();
			createPaymentRequest();
		});
		registerAndDisplayMenu(accountsMenu, "payment.account.menu");
	}

	private static void prepareAccountMenu(Action action) {
		accountsMenu = new ConsoleMenu("Accounts Menu");
		LoadAccountsUseCase loadAccountsUseCase = new LoadAccountsUseCase(context.accountRepository());
		AtomicInteger i = new AtomicInteger();
		loadAccountsUseCase.loadAccounts().stream().forEach(o -> {
			ConsoleOption accountOption = new ConsoleOption(o.getIban(), i.incrementAndGet(), () -> {
				orderingIban = o.getIban();
				action.doAction();
			});
			accountsMenu.addOption(accountOption);
		});
	}

	private static void registerAndDisplayMenu(ConsoleMenu menu, String mappedKeyName) {
		consoleController.registerMenu(menu, mappedKeyName);
		consoleController.setCurrentMenu(mappedKeyName);
		consoleController.displayCurrentMenu();
	}

	private static void createPaymentRequest() {
		PaymentRequest paymentRequestDataStructure = buildPaymentRequestDataStructure();
		paymentRequestDataStructure.setPaymentDate(Date.valueOf(LocalDate.parse(paymentQuestionary.getAnswer(DATE_Q))));
		context.paymentRequestRepositroy().registerChequeWriter(new EnglishChequeAmountWriter());
		CreateNewPaymentRequestUseCase createNewPaymentRequestUseCase = new CreateNewPaymentRequestUseCase(
				context.paymentRequestRepositroy(), context.accountRepository());
		try {
			createNewPaymentRequestUseCase.createPaymentRequestFrom(paymentRequestDataStructure);
		} catch (AccountRuleViolationException e) {
			logger.printErr("The Acount Rules don't allow this move");
			logger.logErr("The Acount Rules don't allow this move", e);
		}

	}

	private static PaymentRequest buildPaymentRequestDataStructure() {
		PaymentRequest paymentRequestDataStructure = new PaymentRequest();
		paymentRequestDataStructure.setPaymentAmount(new BigDecimal(paymentQuestionary.getAnswer(AMNT_Q)));
		paymentRequestDataStructure.setBeneficiaryName(paymentQuestionary.getAnswer(BENEF_NAME));
		paymentRequestDataStructure.setBeneficiaryAccountIBAN(paymentQuestionary.getAnswer(BENEF_IBAN_Q));
		paymentRequestDataStructure.setCurrencyCode(paymentQuestionary.getAnswer(CRNCY_Q));
		paymentRequestDataStructure.setPurposeCode(paymentQuestionary.getAnswer(PURPS_Q));
		paymentRequestDataStructure.setOrderingAccountIban(orderingIban);
		return paymentRequestDataStructure;
	}

	private static void startLoginQuestionary() {
		loginQuestionary.addQuestion(USR_Q);
		loginQuestionary.addQuestion(PASS_Q);
		loginQuestionary.startQuestionary();
	}

	private static void validateLoginAuth() {
		boolean auth = "root".equals(loginQuestionary.getAnswer(USR_Q))
				&& "root".equals(loginQuestionary.getAnswer(PASS_Q));
		if (!auth) {
			logger.printErr("Access Denied");
			consoleController.setCurrentMenu("login.menu");
			consoleController.displayCurrentMenu();
		}
	}

	private static void startPaymentQuestionary() {
		paymentQuestionary.addQuestion(AMNT_Q);
		paymentQuestionary.addQuestion(CRNCY_Q);
		paymentQuestionary.addQuestion(BENEF_IBAN_Q);
		paymentQuestionary.addQuestion(BENEF_NAME);
		paymentQuestionary.addQuestion(PURPS_Q);
		paymentQuestionary.addQuestion(DATE_Q);
		paymentQuestionary.startQuestionary();
	}

	private static AppContext prepareContext() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUsername(DataBaseSettings.getInstance().username());
		dataSource.setPassword(DataBaseSettings.getInstance().password());
		dataSource.setUrl(DataBaseSettings.getInstance().url());
		dataSource.setDriverClassName(DataBaseSettings.getInstance().driverClassName());
		return new AppContextImpl(dataSource);
	}
}
