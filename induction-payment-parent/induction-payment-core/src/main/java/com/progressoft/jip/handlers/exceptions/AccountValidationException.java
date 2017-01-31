package com.progressoft.jip.handlers.exceptions;

public class AccountValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public AccountValidationException() {
		super();
	}

	public AccountValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountValidationException(String message) {
		super(message);
	}

	public AccountValidationException(Throwable cause) {
		super(cause);
	}

}
