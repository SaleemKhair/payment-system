package com.progressoft.jip.jparepositories.exceptions;

public class NoPaymentPurposeUpdatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPaymentPurposeUpdatedException() {
		super();
	}

	public NoPaymentPurposeUpdatedException(String message) {
		super(message);
	}

	public NoPaymentPurposeUpdatedException(Throwable cause) {
		super(cause);
	}

	public NoPaymentPurposeUpdatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPaymentPurposeUpdatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
