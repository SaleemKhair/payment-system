package com.progressoft.jip.jparepositories.exceptions;

public class DuplicatePaymentRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicatePaymentRequestException() {
		super();
	}

	public DuplicatePaymentRequestException(String message) {
		super(message);
	}

	public DuplicatePaymentRequestException(Throwable cause) {
		super(cause);
	}

	public DuplicatePaymentRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatePaymentRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
