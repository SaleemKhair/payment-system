package com.progressoft.jip.utilities.chequewriting.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.progressoft.jip.utilities.chequewriting.ChequeAmountWriter;

public class AbstractAmountWriter {

	private Map<String, ChequeAmountWriter> amountWriters = new HashMap<>();

	public void addWriter(String key, ChequeAmountWriter chequeAmountWriter) {
		amountWriters.put(key, chequeAmountWriter);
	}

	public String writeAmount(String key, String currencyCode, BigDecimal amount) {
		return amountWriters.get(key).writeAmountInWords(amount, currencyCode);
	}

	public List<String> getWritersNames() {
		List<String> writerNames = new ArrayList<>();

		Set<String> keySet = amountWriters.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			writerNames.add(iterator.next());
		}
		return writerNames;
	}
}
