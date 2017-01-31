package com.progressoft.jip.iban.exception;

import com.progressoft.jip.handlers.exceptions.ValidationException;

public class InvalidIBANException extends ValidationException {
	private static final long serialVersionUID = 1L;

	public InvalidIBANException(String e) {
		super(e);
	}

}
