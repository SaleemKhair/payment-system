package com.progressoft.jip.jparepositories.exceptions;

public class NoPaymentRequestDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPaymentRequestDeletedException() {
		super();
	}

	public NoPaymentRequestDeletedException(String message) {
		super(message);
	}

	public NoPaymentRequestDeletedException(Throwable cause) {
		super(cause);
	}

	public NoPaymentRequestDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPaymentRequestDeletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
