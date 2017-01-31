package com.progressoft.jip.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the currency database table.
 * 
 */
@Entity
@Table(name = "currency")

@NamedQueries({ @NamedQuery(name = "CurrencyEntity.findAll", query = "SELECT c FROM CurrencyEntity c"),
		@NamedQuery(name = "CurrencyEntity.findByCode", query = "SELECT c FROM CurrencyEntity c where c.code=:code") })
public class CurrencyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String coinsName;

	private String name;

	// bi-directional many-to-one association to AccountEntity
	@OneToMany(mappedBy = "currency")
	private List<AccountEntity> accounts;

	// bi-directional many-to-one association to PaymentRequestEntity
	@OneToMany(mappedBy = "currency")
	private List<PaymentRequestEntity> paymentRequests;

	public CurrencyEntity() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCoinsName() {
		return this.coinsName;
	}

	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AccountEntity> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<AccountEntity> accounts) {
		this.accounts = accounts;
	}

	public AccountEntity addAccount(AccountEntity account) {
		getAccounts().add(account);
		account.setCurrency(this);

		return account;
	}

	public AccountEntity removeAccount(AccountEntity account) {
		getAccounts().remove(account);
		account.setCurrency(null);

		return account;
	}

	public List<PaymentRequestEntity> getPaymentRequests() {
		return this.paymentRequests;
	}

	public void setPaymentRequests(List<PaymentRequestEntity> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}

	public PaymentRequestEntity addPaymentRequest(PaymentRequestEntity paymentRequest) {
		getPaymentRequests().add(paymentRequest);
		paymentRequest.setCurrency(this);

		return paymentRequest;
	}

	public PaymentRequestEntity removePaymentRequest(PaymentRequestEntity paymentRequest) {
		getPaymentRequests().remove(paymentRequest);
		paymentRequest.setCurrency(null);

		return paymentRequest;
	}

}