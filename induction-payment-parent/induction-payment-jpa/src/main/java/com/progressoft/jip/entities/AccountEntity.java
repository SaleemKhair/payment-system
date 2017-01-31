package com.progressoft.jip.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name = "account")

@NamedQueries({ @NamedQuery(name = "AccountEntity.findAll", query = "SELECT a FROM AccountEntity a"),
		@NamedQuery(name = "AccountEntity.findByIban", query = "SELECT a FROM AccountEntity a WHERE a.iban = :iban") })
public class AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	private BigDecimal balance;

	private String rule;

	private String status;

	private String type;

	@ManyToOne
	@JoinColumn(name = "currencyCode")
	private CurrencyEntity currency;

	@OneToMany(mappedBy = "account")
	private List<PaymentRequestEntity> paymentRequests;

	public AccountEntity() {
		super();
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CurrencyEntity getCurrency() {
		return this.currency;
	}

	public void setCurrency(CurrencyEntity currency) {
		this.currency = currency;
	}

	public List<PaymentRequestEntity> getPaymentRequests() {
		return this.paymentRequests;
	}

	public void setPaymentRequests(List<PaymentRequestEntity> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}

	public PaymentRequestEntity addPaymentRequest(PaymentRequestEntity paymentRequest) {
		getPaymentRequests().add(paymentRequest);
		paymentRequest.setAccount(this);

		return paymentRequest;
	}

	public PaymentRequestEntity removePaymentRequest(PaymentRequestEntity paymentRequest) {
		getPaymentRequests().remove(paymentRequest);
		paymentRequest.setAccount(null);

		return paymentRequest;
	}

}