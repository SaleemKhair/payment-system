package com.progressoft.submitpayment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextJPA;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.usecases.AccountUseCases;
import com.progressoft.jip.usecases.CurrencyUseCases;
import com.progressoft.jip.usecases.PaymentPurposeUseCases;
import com.progressoft.jip.usecases.PaymentRequestUseCases;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public class PaymentRequestSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SUBMIT_PAYMENT_REQUEST_PAGE_WAR = "/web-war/submitpaymentrequest";
	private static final String CREATE_PAYMENT_REQUEST_PAGE = "/WEB-INF/views/createPayment.jsp";
	private static final String BASE_JSP_PAGE = "/WEB-INF/views/base.jsp";
	private static final String PAYMENT_PURPOSES = "paymentPurposes";
	private static final String PAGE_CONTENT = "pageContent";
	private static final String CURRENCIES = "currencies";
	private static final String CHECK_LANG = "checkLang";
	private static final String ACCOUNTS = "accounts";

	private PaymentRequestUseCases paymentRequestUseCases;
	private PaymentPurposeUseCases paymentPurposeUseCases;
	private AccountUseCases accountUseCases;
	private CurrencyUseCases currencyUseCases;
	private AbstractAmountWriter amountWriter;

	@Override
	public void init() throws ServletException {
		AppContext context = AppContextJPA.getContext();
		paymentRequestUseCases = context.getPaymentRequestUseCases();
		paymentPurposeUseCases = context.getPaymentPurposeUseCases();
		accountUseCases = context.getAccountUseCases();
		currencyUseCases = context.getCurrencyUseCases();
		amountWriter = context.getAbstractAmountWriter();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("orderingAccountIban", req.getSession().getAttribute("PaymentIban"));
		req.setAttribute(PAYMENT_PURPOSES, paymentPurposeUseCases.getAllPaymentPurposes());
		req.setAttribute(CURRENCIES, currencyUseCases.getAllCurrencies());
		req.setAttribute(ACCOUNTS, accountUseCases.getAllAccounts());
		req.setAttribute(CHECK_LANG, amountWriter.getWritersNames());

		req.setAttribute(PAGE_CONTENT, CREATE_PAYMENT_REQUEST_PAGE);
		req.getRequestDispatcher(BASE_JSP_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			PaymentRequest paymentRequest = new PaymentRequest();
			BeanUtils.populate(paymentRequest, req.getParameterMap());
			paymentRequestUseCases.createPaymentRequest(paymentRequest, amountWriter, req.getParameter(CHECK_LANG));
			resp.sendRedirect(SUBMIT_PAYMENT_REQUEST_PAGE_WAR);
		} catch (IllegalAccessException | InvocationTargetException | ValidationException e) {
			throw new ServletException(e);
		}
	}

}
