package com.progressoft.jip.rules.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.progressoft.jip.rules.Rule;

public class PaymentRules implements IPaymentRules {

	private static final Map<String, Rule> rulesMap = new HashMap<>();
	private static PaymentRules instance;

	private PaymentRules() {
	}

	public static PaymentRules getInstance() {
		if (Objects.isNull(instance)) {
			instance = new PaymentRules();
		}
		return instance;
	}

	public void registerRule(String key, Rule rule) {
		rulesMap.put(key, rule);
	}

	@Override
	public Rule getRule(String key) {
		if (!rulesMap.containsKey(key))
			throw new RuleDoesNotExistException();
		return rulesMap.get(key);

	}

	public class RuleDoesNotExistException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	@Override
	public List<String> getRuleNames() {
		Set<String> keySet = rulesMap.keySet();
		List<String> ruleNames = new ArrayList<>();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			ruleNames.add(iterator.next());
		}
		return ruleNames;
	}
}