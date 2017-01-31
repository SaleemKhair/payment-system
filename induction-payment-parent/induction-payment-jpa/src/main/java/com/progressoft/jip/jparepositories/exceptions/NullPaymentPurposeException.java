package com.progressoft.jip.jparepositories.exceptions;

public class NullPaymentPurposeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NullPaymentPurposeException() {
		super();
	}

	public NullPaymentPurposeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NullPaymentPurposeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullPaymentPurposeException(String message) {
		super(message);
	}

	public NullPaymentPurposeException(Throwable cause) {
		super(cause);
	}
	
	

}
