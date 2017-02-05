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

	private PaymentRequestUseCases paymentRequestUseCases;
	private PaymentPurposeUseCases paymentPurposeUseCases;
	private AccountUseCases accountUseCases;
	private CurrencyUseCases currencyUseCases;
	private AbstractAmountWriter amountWriter;

	@Override
	public void init() throws ServletException {
		AppContext context =  AppContextJPA.getContext();
		paymentRequestUseCases = context.getPaymentRequestUseCases();
		paymentPurposeUseCases = context.getPaymentPurposeUseCases();
		accountUseCases = context.getAccountUseCases();
		currencyUseCases = context.getCurrencyUseCases();
		amountWriter = context.getAbstractAmountWriter();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("paymentPurposes", paymentPurposeUseCases.getAllPaymentPurposes());
		req.setAttribute("currencies", currencyUseCases.getAllCurrencies());
		req.setAttribute("accounts", accountUseCases.getAllAccounts());
		req.setAttribute("checkLang", amountWriter.getWritersNames());

		req.setAttribute("pageContent", "/WEB-INF/views/createPayment.jsp");
		req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			PaymentRequest paymentRequest = new PaymentRequest();
			BeanUtils.populate(paymentRequest, req.getParameterMap());
			paymentRequestUseCases.createPaymentRequest(paymentRequest, amountWriter, req.getParameter("checkLang"));
			resp.sendRedirect("/web-war/submitpaymentrequest");
		} catch (IllegalAccessException | InvocationTargetException | ValidationException e) {
			throw new ServletException(e);
		}
	}

}
