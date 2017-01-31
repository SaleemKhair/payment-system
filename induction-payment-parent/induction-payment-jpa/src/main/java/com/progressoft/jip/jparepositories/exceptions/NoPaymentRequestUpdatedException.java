package com.progressoft.jip.jparepositories.exceptions;

public class NoPaymentRequestUpdatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPaymentRequestUpdatedException() {
		super();
	}

	public NoPaymentRequestUpdatedException(String message) {
		super(message);
	}

	public NoPaymentRequestUpdatedException(Throwable cause) {
		super(cause);
	}

	public NoPaymentRequestUpdatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPaymentRequestUpdatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
