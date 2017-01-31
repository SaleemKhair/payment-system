package com.progressoft.jip.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.progressoft.jip.gateways.views.AccountView;



public class Account implements Serializable, AccountView {

	private static final long serialVersionUID = 1L;
	private String iban;
	private String type;
	private BigDecimal balance;
	private String status;
	private String currencyCode;
	private String rule;

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@Override
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "AccountDatastructure [iban=" + iban + ", accountType=" + type + ", balance=" + balance
				+ ", status=" + status + ", currencyCode=" + currencyCode + ", rule=" + rule + "]";
	}

}
