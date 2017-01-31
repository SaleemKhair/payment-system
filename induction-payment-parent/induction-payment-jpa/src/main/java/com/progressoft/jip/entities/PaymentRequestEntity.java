package com.progressoft.jip.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the paymentRequest database table.
 * 
 */
@Entity
@Table(name = "paymentRequest")
@NamedQueries({ @NamedQuery(name = "PaymentRequestEntity.findAll", query = "SELECT p FROM PaymentRequestEntity p"),
		@NamedQuery(name = "PaymentRequestEntity.findById", query = "SELECT p FROM PaymentRequestEntity p where p.id=:id"),
		@NamedQuery(name = "PaymentRequestEntity.findByDate", query = "SELECT p FROM PaymentRequestEntity p where p.paymentDate=:paymentDate"),
		@NamedQuery(name = "PaymentRequest.findByOrderingIBAN", query = "select p from PaymentRequestEntity p where p.account.iban =:iban") })
public class PaymentRequestEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String amountInWords;

	private String beneficiaryAccountIban;

	private String beneficiaryName;

	private BigDecimal paymentAmount;

	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	// bi-directional many-to-one association to PaymentPurposeEntity
	@ManyToOne
	@JoinColumn(name = "purposeCode")
	private PaymentPurposeEntity paymentPurpose;

	// bi-directional many-to-one association to CurrencyEntity
	@ManyToOne
	private CurrencyEntity currency;

	// bi-directional many-to-one association to AccountEntity
	@ManyToOne
	@JoinColumn(name = "orderingAccount_iban")
	private AccountEntity account;

	public PaymentRequestEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAmountInWords() {
		return this.amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getBeneficiaryAccountIban() {
		return this.beneficiaryAccountIban;
	}

	public void setBeneficiaryAccountIban(String beneficiaryAccountIban) {
		this.beneficiaryAccountIban = beneficiaryAccountIban;
	}

	public String getBeneficiaryName() {
		return this.beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public BigDecimal getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentPurposeEntity getPaymentPurpose() {
		return this.paymentPurpose;
	}

	public void setPaymentPurpose(PaymentPurposeEntity paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	public CurrencyEntity getCurrency() {
		return this.currency;
	}

	public void setCurrency(CurrencyEntity currency) {
		this.currency = currency;
	}

	public AccountEntity getAccount() {
		return this.account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

}