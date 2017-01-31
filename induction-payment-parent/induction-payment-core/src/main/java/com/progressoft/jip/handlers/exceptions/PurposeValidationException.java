package com.progressoft.jip.handlers.exceptions;

public class PurposeValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public PurposeValidationException() {
		super();
	}

	public PurposeValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PurposeValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PurposeValidationException(String message) {
		super(message);
	}

	public PurposeValidationException(Throwable cause) {
		super(cause);
	}

}
