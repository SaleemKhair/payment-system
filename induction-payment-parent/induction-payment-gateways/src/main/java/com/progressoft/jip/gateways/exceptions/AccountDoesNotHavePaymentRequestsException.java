package com.progressoft.jip.gateways.exceptions;

public class AccountDoesNotHavePaymentRequestsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AccountDoesNotHavePaymentRequestsException() {
		super();
	}

	public AccountDoesNotHavePaymentRequestsException(String message) {
		super(message);
	}

	public AccountDoesNotHavePaymentRequestsException(Throwable cause) {
		super(cause);
	}

	public AccountDoesNotHavePaymentRequestsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountDoesNotHavePaymentRequestsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
