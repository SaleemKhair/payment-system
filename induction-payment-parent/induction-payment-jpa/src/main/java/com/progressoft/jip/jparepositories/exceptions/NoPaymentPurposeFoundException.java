package com.progressoft.jip.jparepositories.exceptions;

public class NoPaymentPurposeFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoPaymentPurposeFoundException() {
		super();
	}

	public NoPaymentPurposeFoundException(String message) {
		super(message);
	}

	public NoPaymentPurposeFoundException(Throwable cause) {
		super(cause);
	}

	public NoPaymentPurposeFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPaymentPurposeFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
