package com.progressoft.jip.jobwatcher.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.gateways.AccountGateway;
import com.progressoft.jip.gateways.views.AccountView;

public class AccountGatewayMock implements AccountGateway {
	
	private List<AccountView> accounts = new ArrayList<>();
	public AccountGatewayMock() {
		String[] ibans = { "JO94CBJO0010000000000131000302", "AD1200012030200359100100","AT611904300234573201","BE62510007547061"};
		for(String iban : ibans){
			accounts.add(newAccount(iban));
		}
	}

	@Override
	public AccountView loadAccountByIban(String iban) {
		for(AccountView account:accounts){
			if(account.getIban().equals(iban)){
				return account;
			}
		}
		return null;
	}

	@Override
	public Collection<AccountView> loadAccounts() {
		return new ArrayList<>(accounts);
	}

	@Override
	public void updateAccount(AccountView account) {
		String ibanToUpdate = account.getIban();
		for (AccountView a : accounts) {
			if(a.getIban().equals(ibanToUpdate)){
				accounts.remove(a);
				accounts.add(account);
			}
		}
	}

	@Override
	public void createAccount(AccountView newAccount) {
		accounts.add(newAccount);
	}

	
	private Account newAccount(String iban) {
		Account originalAccount = new Account();
		originalAccount.setIban(iban);
		originalAccount.setType("TYPE");
		originalAccount.setCurrencyCode("USD");
		originalAccount.setBalance(BigDecimal.valueOf(1000));
		originalAccount.setStatus("ACTIVE");
		originalAccount.setRule("five.months.ahead");
		return originalAccount;
	}
}
