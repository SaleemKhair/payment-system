package com.progressoft.jip.jparepositories.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException() {
		super();
	}

	public AccountAlreadyExistsException(String message) {
		super(message);
	}

	public AccountAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public AccountAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
