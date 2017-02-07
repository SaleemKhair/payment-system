package com.progressoft.jip.handlers.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.PaymentImportHandler;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.usecases.PaymentRequestUseCases;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public class XmlPaymentImportHandler implements PaymentImportHandler {
	private enum ReportResult {
		SUCCESS, FAILED, PARTIALLY_SUCCESSFULL
	}

	private static final String REPORT_RESULT = "reportResult";
	private static final String CONSTRAINT = "constraint";
	private static final String ROW_NUMBER = "rowNumber";
	private static final String ENCODING_VERSION = "1.0";
	private static final String ENCODING_TYPE = "UTF-8";
	private static final String ERRORS = "errors";
	private static final String REPORT = "Report";
	private static final String ERROR = "error";

	private PaymentRequestUseCases paymentRequestUseCases;
	private AbstractAmountWriter amountWriter;
	private XMLStreamWriter xMLStreamWriter;
	private boolean atLeastOneRecordPassed;
	private Writer stringWriter;
	private int errorCount = 0;
	private String writerKey;

	public XmlPaymentImportHandler(PaymentRequestUseCases paymentRequestUseCases, AbstractAmountWriter amountWriter,
			String writerKey) {
		stringWriter = new StringWriter();
		this.paymentRequestUseCases = paymentRequestUseCases;
		this.amountWriter = amountWriter;
		this.writerKey = writerKey;
	}

	@Override
	public void onSuccess() {
		atLeastOneRecordPassed = true;
	}

	@Override
	public String displayReport() {
		return stringWriter.toString();
	}

	@Override
	public void accept(int rowNumber, PaymentRequest paymentRequest) {
		Set<ConstraintViolation<PaymentRequest>> validatePaymentRequest = validatePaymentRequest(paymentRequest);
		if (validatePaymentRequest.isEmpty()) {
			onSuccess();
			insertPaymentRequest(paymentRequest);
		} else {
			onError(rowNumber, validatePaymentRequest);
		}
	}

	@Override
	public void onReportEnd() {
		String parsingResult;
		try {
			if (errorCount > 0)
				xMLStreamWriter.writeEndElement();
			if (errorCount == 0 && atLeastOneRecordPassed)
				parsingResult = ReportResult.SUCCESS.name();
			else if (atLeastOneRecordPassed)
				parsingResult = ReportResult.PARTIALLY_SUCCESSFULL.name();
			else
				parsingResult = ReportResult.FAILED.name();

			xMLStreamWriter.writeStartElement(REPORT_RESULT);
			xMLStreamWriter.writeCharacters(parsingResult);
			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.flush();
			xMLStreamWriter.close();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void onReportStart() {
		try {
			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
			xMLStreamWriter.writeStartDocument(ENCODING_TYPE, ENCODING_VERSION);
			xMLStreamWriter.writeStartElement(REPORT);
		} catch (XMLStreamException e) {
			throw new IllegalStateException(e);
		}
	}

	private void writeConstraintsViolations(Iterator<ConstraintViolation<PaymentRequest>> iterator) {
		iterator.forEachRemaining(v -> {
			try {
				xMLStreamWriter.writeStartElement(CONSTRAINT);
				xMLStreamWriter.writeCharacters(v.getMessage());
				xMLStreamWriter.writeEndElement();
			} catch (XMLStreamException e) {
				throw new IllegalStateException(e);
			}
		});
	}

	private void insertPaymentRequest(PaymentRequest paymentRequest) {
		try {
			paymentRequestUseCases.createPaymentRequest(paymentRequest, amountWriter, writerKey);
		} catch (ValidationException e) {
			throw new IllegalStateException(e);
		}
	}

	private Set<ConstraintViolation<PaymentRequest>> validatePaymentRequest(PaymentRequest paymentRequest) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(paymentRequest);
	}

	private void onError(int rowNumber, Iterable<ConstraintViolation<PaymentRequest>> violations) {
		try {
			if (errorCount == 0)
				xMLStreamWriter.writeStartElement(ERRORS);
			xMLStreamWriter.writeStartElement(ERROR);
			xMLStreamWriter.writeAttribute(ROW_NUMBER, String.valueOf(rowNumber));
			writeConstraintsViolations(violations.iterator());
			xMLStreamWriter.writeEndElement();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		errorCount++;

	}

}
