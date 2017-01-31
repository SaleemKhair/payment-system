package com.progressoft.jip.gateways.jpa.converters.exceptions;

public class BeanConversionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BeanConversionException() {
		super();
	}

	public BeanConversionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BeanConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanConversionException(String message) {
		super(message);
	}

	public BeanConversionException(Throwable cause) {
		super(cause);
	}

}
