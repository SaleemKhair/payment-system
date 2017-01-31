package com.progressoft.jip.gateways.views;

import java.math.BigDecimal;

public interface AccountView {

	String getIban();

	BigDecimal getBalance();

	String getCurrencyCode();

	String getStatus();

	String getType();

	String getRule();

}
