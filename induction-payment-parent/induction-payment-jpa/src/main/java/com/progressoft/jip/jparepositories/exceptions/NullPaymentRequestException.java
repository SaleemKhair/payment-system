package com.progressoft.jip.jparepositories.exceptions;

public class NullPaymentRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullPaymentRequestException() {
		super();
	}

	public NullPaymentRequestException(String message) {
		super(message);
	}

	public NullPaymentRequestException(Throwable cause) {
		super(cause);
	}

	public NullPaymentRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullPaymentRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
