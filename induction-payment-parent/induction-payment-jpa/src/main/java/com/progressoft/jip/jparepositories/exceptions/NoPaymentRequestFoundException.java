package com.progressoft.jip.jparepositories.exceptions;

public class NoPaymentRequestFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPaymentRequestFoundException() {
		super();
	}

	public NoPaymentRequestFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoPaymentRequestFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPaymentRequestFoundException(String message) {
		super(message);
	}

	public NoPaymentRequestFoundException(Throwable cause) {
		super(cause);
	}
	

}
