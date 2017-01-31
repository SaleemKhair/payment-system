package com.progressoft.jip.gateways.jpa.converters.exceptions;

public class EntityConversionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityConversionException() {
		super();
	}

	public EntityConversionException(String message) {
		super(message);
	}

	public EntityConversionException(Throwable cause) {
		super(cause);
	}

	public EntityConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityConversionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
