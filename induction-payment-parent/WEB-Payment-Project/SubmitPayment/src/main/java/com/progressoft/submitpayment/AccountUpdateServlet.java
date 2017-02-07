package com.progressoft.submitpayment;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextJPA;
import com.progressoft.jip.handlers.exceptions.ValidationException;

public class AccountUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPDATE_ACCOUNT_PAGE = "/WEB-INF/views/updateAccount.jsp";
	private static final String UPDATE_ACCOUNT_WAR_PAGE = "/web-war/updateAccount";
	private static final String BASE_JSP_PAGE = "/WEB-INF/views/base.jsp";
	private static final String SELECTED_ACCOUNT = "selectedAccount";
	private static final String CURRENCY_CODE = "currencyCode";
	private static final String EDIT_ACCOUNT = "editAccount";
	private static final String PAGE_CONTENT = "pageContent";
	private static final String CURRENCIES = "currencies";
	private static final String ACCOUNTS = "accounts";
	private static final String IBAN_NUMBER = "iban";
	private static final String RULES = "rules";
	private static final String RULE = "rule";
	private static final String TYPE = "type";

	private String iban;
	private AppContext context;

	@Override
	public void init() throws ServletException {
		context = AppContextJPA.getContext();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		iban = req.getParameter(IBAN_NUMBER);
		req.setAttribute(IBAN_NUMBER, iban);
		populateAttributes(req);
		req.getRequestDispatcher(BASE_JSP_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (Objects.nonNull(req.getParameter(EDIT_ACCOUNT))) {
			Account account = populateAccount(req);
			try {
				context.getAccountUseCases().editAccount(account);
			} catch (ValidationException e) {
				throw new IllegalStateException(e);
			}
		}
		resp.sendRedirect(UPDATE_ACCOUNT_WAR_PAGE);
	}

	private void populateAttributes(HttpServletRequest req) throws ServletException, IOException {
		if (Objects.nonNull(iban))
			req.setAttribute(SELECTED_ACCOUNT, context.getAccountUseCases().getAccountByIban(iban));
		req.setAttribute(ACCOUNTS, context.getAccountUseCases().getAllAccounts());
		req.setAttribute(CURRENCIES, context.getCurrencyUseCases().getAllCurrencies());
		req.setAttribute(RULES, context.getPaymentRuleNames());
		req.setAttribute(PAGE_CONTENT, UPDATE_ACCOUNT_PAGE);
	}

	private Account populateAccount(HttpServletRequest req) {
		Account account = new Account();
		account.setIban(iban);
		account.setBalance(context.getAccountUseCases().getAccountByIban(iban).getBalance());
		account.setStatus(context.getAccountUseCases().getAccountByIban(iban).getStatus());
		account.setType(req.getParameter(TYPE));
		account.setCurrencyCode(req.getParameter(CURRENCY_CODE));
		account.setRule(req.getParameter(RULE));
		return account;
	}

}
