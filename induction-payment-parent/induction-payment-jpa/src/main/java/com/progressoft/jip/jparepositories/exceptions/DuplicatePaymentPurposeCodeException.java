package com.progressoft.jip.jparepositories.exceptions;

public class DuplicatePaymentPurposeCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
	public DuplicatePaymentPurposeCodeException() {
		super();
	}

	public DuplicatePaymentPurposeCodeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicatePaymentPurposeCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatePaymentPurposeCodeException(String message) {
		super(message);
	}

	public DuplicatePaymentPurposeCodeException(Throwable cause) {
		super(cause);
	}

}
