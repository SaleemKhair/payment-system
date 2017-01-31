package com.progressoft.jip.iban;

import com.progressoft.jip.handlers.validators.Validator;
import com.progressoft.jip.iban.exception.InvalidIBANException;
import com.progressoft.jip.iban.impl.IBANFormatValidator;
import com.progressoft.jip.iban.impl.IBANModValidator;

public class IBANGeneralValidator implements Validator<String, InvalidIBANException> {

	IBANValidator formatsValidator = new IBANFormatValidator();
	IBANValidator modValidator = new IBANModValidator();

	@Override
	public void validate(String iban) throws InvalidIBANException {
		if (!formatsValidator.isValid(iban) || !modValidator.isValid(iban)) {
			throw new InvalidIBANException(iban);
		}
	}

}
