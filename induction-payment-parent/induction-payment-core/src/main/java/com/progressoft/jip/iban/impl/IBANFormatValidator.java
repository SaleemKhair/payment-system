package com.progressoft.jip.iban.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.progressoft.jip.iban.IBANCountryFormatsReader;
import com.progressoft.jip.iban.IBANValidator;
import com.progressoft.jip.iban.IBANVersion;
import com.progressoft.jip.iban.exception.EmptyIBANException;
import com.progressoft.jip.iban.exception.NullIBANException;
import com.progressoft.jip.iban.exception.TooShortIBANException;

@IBANVersion("ISO13616")
public class IBANFormatValidator implements IBANValidator {
	private static final String FORMAT_REGEX = "([0-9]{1,2})!([nac])";
	private static final int NUMBER_GROUP = 1;
	private static final int TYPE_GROUP = 2;
	private IBANCountryFormatsReader countryFormats = new IBANCountryFormatsReaderImp();

	@Override
	public boolean isValid(String iban) {
		if (Objects.isNull(iban))
			throw new NullIBANException();
		if (iban.trim().isEmpty())
			throw new EmptyIBANException();
		if (iban.length() < 2)
			throw new TooShortIBANException();
		String format = countryFormats.getIBANFormat(iban.substring(0, 2));
		if (Pattern.matches(generateRegEx(format), iban))
			return true;
		return false;
	}

	private String generateRegEx(String format) {
		StringBuilder regex = new StringBuilder();
		compileFormatPattern(format, regex);
		return regex.toString();
	}

	private void compileFormatPattern(String format, StringBuilder regex) {
		Matcher numberMatcher = Pattern.compile(FORMAT_REGEX).matcher(format);
		while (numberMatcher.find())
			getAndReplaceMatch(regex, numberMatcher);
	}

	private void getAndReplaceMatch(StringBuilder regex, Matcher numberMatcher) {
		String number = numberMatcher.group(NUMBER_GROUP);
		String type = numberMatcher.group(TYPE_GROUP);
		numberMatcher.appendReplacement(new StringBuffer(regex.toString()), getReplacement(number, type));
	}

	private String getReplacement(String number, String type) {
		if ("n".equals(type))
			return "[0-9]{" + number + "}";
		if ("a".equals(type))
			return "[a-zA-Z]{" + number + "}";
		if ("c".equals(type))
			return "[0-9a-zA-Z]{" + number + "}";
		return null;
	}
}
