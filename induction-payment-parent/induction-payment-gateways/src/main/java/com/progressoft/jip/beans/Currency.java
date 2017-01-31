package com.progressoft.jip.beans;

import java.io.Serializable;

import com.progressoft.jip.gateways.views.CurrencyView;

public class Currency implements Serializable, CurrencyView {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String coinsName;

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCoinsName() {
		return coinsName;
	}

}
