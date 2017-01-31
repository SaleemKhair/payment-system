package com.progressoft.jip.rules.impl;

import java.util.List;

import com.progressoft.jip.rules.Rule;

public interface IPaymentRules {

	Rule getRule(String key);

	List<String> getRuleNames();

}