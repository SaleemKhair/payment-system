package com.progressoft.jip.iban;

@FunctionalInterface
public interface IBANValidator {

	public boolean isValid(String iban);

}
