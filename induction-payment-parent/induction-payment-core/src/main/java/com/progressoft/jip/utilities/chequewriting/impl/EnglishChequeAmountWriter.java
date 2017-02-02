package com.progressoft.jip.utilities.chequewriting.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Currency;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.progressoft.jip.utilities.chequewriting.ChequeAmountWriter;

public class EnglishChequeAmountWriter implements ChequeAmountWriter {
	private Deque<String> deque;
	private Map<Integer, String> postFixes = new HashMap<>();
	private Map<Integer, String> currencyMap = new HashMap<>();
	private Properties properties;
	private Properties coinDictionary;

	public EnglishChequeAmountWriter() {
		properties = new Properties();
		coinDictionary = new Properties();
		try {
			properties.load(EnglishChequeAmountWriter.class.getClassLoader().getResourceAsStream(
					"com/progressoft/jip/utilities/Dictionaries/chequeWriterDictionary.properties"));
			coinDictionary.load(EnglishChequeAmountWriter.class.getClassLoader().getResourceAsStream(
					"com/progressoft/jip/utilities/Dictionaries/moneyChangeDictionary.properties"));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		postFixes.put(0, "");
		postFixes.put(1, " Thousand ");
		postFixes.put(2, " Million ");
		postFixes.put(3, " Billion ");
		postFixes.put(4, " Trillion ");
	}

	@Override
	public String writeAmountInWords(BigDecimal amount, String currencyCode) {
		Currency currencyInstance = Currency.getInstance(currencyCode);
		currencyMap.put(0, " " + currencyInstance.getDisplayName());
		currencyMap.put(1, " " + coinDictionary.getProperty(currencyCode));
		String[] numberIntegerAndDecimalParts = String.valueOf(amount).split("\\.");
		StringBuilder finalNumber = new StringBuilder();
		for (int i = 0; i < numberIntegerAndDecimalParts.length; ++i) {
			Integer integer = Integer.parseInt(numberIntegerAndDecimalParts[i]);
			finalNumber.append(format(integer));
			if (!integer.equals(0)) {
				finalNumber.append(currencyMap.get(i) + " ");
			}
		}
		return finalNumber.toString();
	}

	private String format(int number) {
		fillDeque(number);
		StringBuilder res = new StringBuilder();
		return processNumber(res);
	}

	private void fillDeque(int number) {
		deque = new ArrayDeque<>();
		int splitted = number;
		while (splitted > 0) {
			deque.push(String.valueOf(splitted % 1000));
			splitted /= 1000;
		}
	}

	private String processNumber(StringBuilder res) {
		while (!deque.isEmpty()) {
			int numAsInteger = Integer.parseInt(deque.pop());
			res.append(calc(numAsInteger));
			if (numAsInteger != 0)
				res.append(getPostfix(deque.size()));
		}
		return res.toString();
	}

	public String getPostfix(int i) {
		if (i < 1)
			return "";
		return postFixes.get(i);
	}

	private String calc(int number) {
		if (number == 0)
			return "";
		int len = (int) (Math.log10(number) + 1);
		if (len == 1)
			return properties.getProperty(String.valueOf(number));
		if (len == 2) {
			if (properties.getProperty(String.valueOf(number)) != null)
				return properties.getProperty(String.valueOf(number));
			return properties.getProperty(String.valueOf(number / 10 * 10)) + " " + calc(number % 10);
		}
		String ret = properties.getProperty(String.valueOf(number / 100)) + " Hundred";
		if (number % 100 == 0)
			return ret;
		return ret + " " + calc(number % 100);
	}

}