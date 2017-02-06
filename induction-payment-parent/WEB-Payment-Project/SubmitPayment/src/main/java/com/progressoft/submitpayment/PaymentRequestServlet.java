package com.progressoft.submitpayment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextJPA;
import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.progressoft.jip.repository.exceptions.RepositoryException;
import com.progressoft.jip.usecases.PaymentRequestUseCases;

public class PaymentRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppContext context;
	private String selectedIban;
	private PaymentRequestUseCases paymentRequestUseCases;
	private static final Logger logger = Logger.getLogger(PaymentRequestServlet.class.getName());

	@Override
	public void init() throws ServletException {
		context =  AppContextJPA.getContext();
		paymentRequestUseCases = context.getPaymentRequestUseCases();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		selectedIban = req.getParameter("iban");
		req.setAttribute("selectedIban", selectedIban);
		if (selectedIban != null) {
			try {
				Collection<PaymentRequestView> paymentRequests = paymentRequestUseCases
						.getPaymentRequestsByOrderingAccountIban(selectedIban);
				req.setAttribute("paymentRequests", paymentRequests);
			} catch (RepositoryException e) {
				throw new IllegalArgumentException(e);
			}
		}
		req.setAttribute("accounts", context.getAccountUseCases().getAllAccounts());
		req.setAttribute("pageContent", "/WEB-INF/views/paymentRequest.jsp");
		req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String formatType = req.getParameter("action");
		if (Objects.nonNull(selectedIban)) {
			try {
				String generateReport = paymentRequestUseCases.generateReport(selectedIban, formatType);
				setHeaders(resp, selectedIban, formatType);
				buildResponseContent(resp, generateReport);
			} catch (RepositoryException e) {
				logger.info(e);
			}
		} else {
			resp.sendRedirect("/web-war/paymentRequest");
		}
	}

	private void buildResponseContent(HttpServletResponse resp, String generateReport) throws IOException {
		ServletOutputStream outputStream = resp.getOutputStream();
		for (char c : generateReport.toCharArray()) {
			outputStream.write(c);
		}
		outputStream.flush();
		outputStream.close();
	}

	private void setHeaders(HttpServletResponse resp, String accountIBAN, String extension) {
		resp.setContentType("text/" + extension);
		String dateTime = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
		resp.setHeader("Content-Disposition", "attachment; filename=" + dateTime + "-" + accountIBAN + "." + extension);
	}

}
