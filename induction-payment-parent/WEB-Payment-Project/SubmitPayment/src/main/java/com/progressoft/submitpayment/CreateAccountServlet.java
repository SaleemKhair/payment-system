package com.progressoft.submitpayment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextJPA;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.usecases.AccountUseCases;
import com.progressoft.jip.usecases.CurrencyUseCases;

@WebServlet(urlPatterns = "/createAccount")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CREATE_ACCOUNT_PAGE = "/WEB-INF/views/createAccount.jsp";
	private static final String CREATE_ACCOUNT_WAR = "/web-war/createAccount";
	private static final String BASE_JSP_PAGE = "/WEB-INF/views/base.jsp";
	private static final String PAGE_CONTENT = "pageContent";
	private static final String CURRENCIES = "currencies";
	private static final String RULES = "rules";

	private CurrencyUseCases currencyUseCases;
	private AccountUseCases accountUseCases;
	private AppContext context;

	@Override
	public void init() throws ServletException {
		context = AppContextJPA.getContext();
		accountUseCases = context.getAccountUseCases();
		currencyUseCases = context.getCurrencyUseCases();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(RULES, context.getPaymentRuleNames());
		req.setAttribute(CURRENCIES, currencyUseCases.getAllCurrencies());
		req.setAttribute(PAGE_CONTENT, CREATE_ACCOUNT_PAGE);
		req.getRequestDispatcher(BASE_JSP_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Account account = new Account();
			BeanUtils.populate(account, req.getParameterMap());
			accountUseCases.createAccount(account);
			resp.sendRedirect(CREATE_ACCOUNT_WAR);
		} catch (IllegalAccessException | InvocationTargetException | ValidationException e) {
			throw new IllegalStateException(e);
		}

	}
}
