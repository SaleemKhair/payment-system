package com.progressoft.submitpayment;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.context.AppContextJPA;
import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.progressoft.jip.repository.exceptions.RepositoryException;
import com.progressoft.jip.usecases.PaymentRequestUseCases;

public class PaymentRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PAYMENT_REQUEST_PAGE = "/WEB-INF/views/paymentRequest.jsp";
	private static final String ATTACHMENT_FILENAME = "attachment; filename=";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String BASE_JSP_PAGE = "/WEB-INF/views/base.jsp";
	private static final String IMPORT_REPORT_XML = "Import-report.xml";
	private static final String PAYMENT_REQUESTS = "paymentRequests";
	private static final String SELECTED_IBAN = "selectedIban";
	private static final String DATE_FORMAT = "yyyyMMddhhmm";
	private static final String PAGE_CONTENT = "pageContent";
	private static final String FILE_UPLOAD = "fileUpload";
	private static final String ACCOUNTS = "accounts";
	private static final String TEXT_XML = "text/xml";
	private static final String ACTION = "action";
	private static final String TEXT = "text/";
	private static final String IBAN = "iban";

	private PaymentRequestUseCases paymentRequestUseCases;
	private String selectedIban;
	private AppContext context;

	@Override
	public void init() throws ServletException {
		context = AppContextJPA.getContext();
		paymentRequestUseCases = context.getPaymentRequestUseCases();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		selectedIban = req.getParameter(IBAN);
		HttpSession session = req.getSession();
		session.setAttribute("PaymentIban", selectedIban);

		req.setAttribute(SELECTED_IBAN, selectedIban);
		if (Objects.nonNull(selectedIban)) {
			try {
				Collection<PaymentRequestView> paymentRequests = paymentRequestUseCases
						.getPaymentRequestsByOrderingAccountIban(selectedIban);
				req.setAttribute(PAYMENT_REQUESTS, paymentRequests);
			} catch (RepositoryException e) {
				throw new IllegalArgumentException(e);
			}
		}
		req.setAttribute(ACCOUNTS, context.getAccountUseCases().getAllAccounts());
		req.setAttribute(PAGE_CONTENT, PAYMENT_REQUEST_PAGE);
		req.getRequestDispatcher(BASE_JSP_PAGE).forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String formatType = req.getParameter(ACTION);
		if (Objects.isNull(formatType)) {
			Part fileUploaded = req.getPart(FILE_UPLOAD);
			if (Objects.nonNull(fileUploaded)) {
				InputStream stream = fileUploaded.getInputStream();
				try {
					paymentRequestUseCases.importPayemntsRequests(stream, context.getImportHandler(),
							context.getImporter());
					String paymentImportReport = paymentRequestUseCases
							.generatePaymentImportReport(context.getImportHandler());
					resp.setContentType(TEXT_XML);
					resp.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + LocalDateTime.now() + IMPORT_REPORT_XML);
					buildResponseContent(resp, paymentImportReport);
				} catch (ParseException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		if (Objects.nonNull(selectedIban)) {
			try {
				String generateReport = paymentRequestUseCases.generateReport(selectedIban, formatType);
				setHeaders(resp, selectedIban, formatType);
				buildResponseContent(resp, generateReport);
			} catch (RepositoryException e) {
				throw new IllegalStateException(e);
			}
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
		resp.setContentType(TEXT + extension);
		String dateTime = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		resp.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + dateTime + "-" + accountIBAN + "." + extension);

	}

}
