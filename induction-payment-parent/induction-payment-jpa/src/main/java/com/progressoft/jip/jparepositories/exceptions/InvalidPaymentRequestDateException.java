package com.progressoft.jip.jparepositories.exceptions;

public class InvalidPaymentRequestDateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPaymentRequestDateException() {
		super();
	}

	public InvalidPaymentRequestDateException(String message) {
		super(message);
	}

	public InvalidPaymentRequestDateException(Throwable cause) {
		super(cause);
	}

	public InvalidPaymentRequestDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPaymentRequestDateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
