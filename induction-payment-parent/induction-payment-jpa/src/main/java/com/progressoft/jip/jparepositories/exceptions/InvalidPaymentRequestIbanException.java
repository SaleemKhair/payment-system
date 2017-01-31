package com.progressoft.jip.jparepositories.exceptions;

public class InvalidPaymentRequestIbanException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPaymentRequestIbanException() {
		super();
	}

	public InvalidPaymentRequestIbanException(String message) {
		super(message);
	}

	public InvalidPaymentRequestIbanException(Throwable cause) {
		super(cause);
	}

	public InvalidPaymentRequestIbanException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPaymentRequestIbanException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
