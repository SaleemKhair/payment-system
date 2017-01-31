package com.progressoft.jip.handlers.exceptions;

public class PaymentValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public PaymentValidationException() {
		super();
	}

	public PaymentValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public PaymentValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentValidationException(String message) {
		super(message);
	}

	public PaymentValidationException(Throwable cause) {
		super(cause);
	}

}
