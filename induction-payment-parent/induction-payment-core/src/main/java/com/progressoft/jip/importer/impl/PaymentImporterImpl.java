package com.progressoft.jip.importer.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.PaymentImportHandler;
import com.progressoft.jip.importer.PaymentImporter;

/**
 * For each request create a new importer
 * 
 * @author khaled
 *
 */
public class PaymentImporterImpl implements PaymentImporter {

	private static final String DATE_FORAMT = "yyyy-MM-dd";
	private static final String VALUE_SPERATOR = ",";
	private static final int BENEFICIARY_IBAN = 1;
	private static final int BENEFICIARY_NAME = 2;
	private static final int PAYMENT_AMOUNT = 4;
	private static final int ORDARING_IBAN = 0;
	private static final int CURRENCY_CODE = 6;
	private static final int PURPOSE_CODE = 5;
	private static final int PAYMENT_DATE = 3;

	private String[] split;
	private int rowNumber = 0;

	@Override
	public synchronized void parse(InputStream stream, PaymentImportHandler paymenthandler) throws ParseException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			paymenthandler.onReportStart();
			processRecords(reader, paymenthandler);
			paymenthandler.onReportEnd();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	private PaymentRequest constructPaymentRequest() throws ParseException {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setOrderingAccountIban(split[ORDARING_IBAN]);
		paymentRequest.setBeneficiaryAccountIban(split[BENEFICIARY_IBAN]);
		paymentRequest.setBeneficiaryName(split[BENEFICIARY_NAME]);
		paymentRequest.setPaymentDate(convertToSqlDate());
		paymentRequest.setPaymentAmount(new BigDecimal(split[PAYMENT_AMOUNT]));
		paymentRequest.setPurposeCode(split[PURPOSE_CODE]);
		paymentRequest.setCurrencyCode(split[CURRENCY_CODE]);
		return paymentRequest;
	}

	private java.sql.Date convertToSqlDate() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORAMT);
		return new java.sql.Date(dateFormat.parse(split[PAYMENT_DATE]).getTime());
	}

	private void processRecords(BufferedReader reader, PaymentImportHandler paymentHandler)
			throws IOException, ParseException {
		String line;
		while (Objects.nonNull(line = reader.readLine())) {
			rowNumber++;
			split = line.split(VALUE_SPERATOR);
			PaymentRequest paymentRequest = constructPaymentRequest();
			paymentHandler.accept(rowNumber, paymentRequest);
		}
	}

}
