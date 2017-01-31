package com.progressoft.jip.jparepositories.exceptions;

public class NullAccountIBANException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullAccountIBANException() {
		super();
	}

	public NullAccountIBANException(String message) {
		super(message);
	}

	public NullAccountIBANException(Throwable cause) {
		super(cause);
	}

	public NullAccountIBANException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullAccountIBANException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
