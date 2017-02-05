package com.progressoft.submitpayment;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextJPA;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.handlers.exceptions.ValidationException;

public class AccountUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_CONTENT = "pageContent";
	private String iban;
	private AppContext context;
	private static final Logger logger = Logger.getLogger(AccountUpdateServlet.class.getName());

	@Override
	public void init() throws ServletException {
		context = AppContextJPA.getContext();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		iban = req.getParameter("iban");
		req.setAttribute("iban", iban);

		if (Objects.nonNull(iban)) {
			req.setAttribute("selectedAccount", context.getAccountUseCases().getAccountByIban(iban));
		}
		req.setAttribute("accounts", context.getAccountUseCases().getAllAccounts());
		req.setAttribute("currencies", context.getCurrencyUseCases().getAllCurrencies());
		req.setAttribute("rules", context.getPaymentRuleNames());

		req.setAttribute(PAGE_CONTENT, "/WEB-INF/views/updateAccount.jsp");
		req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (Objects.nonNull(req.getParameter("editAccount"))) {
			String accountType = req.getParameter("type");
			String currencyCode = req.getParameter("currencyCode");
			String rule = req.getParameter("rule");
			AccountView selectedAccount = context.getAccountUseCases().getAccountByIban(iban);
			Account account = new Account();
			account.setIban(iban);
			account.setBalance(selectedAccount.getBalance());
			account.setStatus(selectedAccount.getStatus());
			account.setType(accountType);
			account.setCurrencyCode(currencyCode);
			account.setRule(rule);
			try {
				context.getAccountUseCases().editAccount(account);
			} catch (ValidationException e) {
				logger.info(e);
			}
		}
		resp.sendRedirect("/web-war/updateAccount");
	}
}
