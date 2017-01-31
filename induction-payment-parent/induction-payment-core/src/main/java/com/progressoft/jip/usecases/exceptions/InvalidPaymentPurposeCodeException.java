package com.progressoft.jip.usecases.exceptions;

import com.progressoft.jip.handlers.exceptions.PurposeValidationException;

public class InvalidPaymentPurposeCodeException extends PurposeValidationException {

	private static final long serialVersionUID = 1L;

	public InvalidPaymentPurposeCodeException() {
		super();
	}

	public InvalidPaymentPurposeCodeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPaymentPurposeCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPaymentPurposeCodeException(String message) {
		super(message);
	}

	public InvalidPaymentPurposeCodeException(Throwable cause) {
		super(cause);
	}

}
