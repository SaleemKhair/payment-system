package com.progressoft.jip.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "paymentRequest")
@NamedQueries({ @NamedQuery(name = "PaymentRequestEntity.findAll", query = "SELECT p FROM PaymentRequestEntity p"),
		@NamedQuery(name = "PaymentRequestEntity.findById", query = "SELECT p FROM PaymentRequestEntity p where p.id=:id"),
		@NamedQuery(name = "PaymentRequestEntity.findByDate", query = "SELECT p FROM PaymentRequestEntity p where p.paymentDate=:paymentDate"),
		@NamedQuery(name = "PaymentRequest.findByOrderingIBAN", query = "select p from PaymentRequestEntity p where p.account.iban =:iban") })
public class PaymentRequestEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String amountInWords;

	private String beneficiaryAccountIban;

	private String beneficiaryName;

	private BigDecimal paymentAmount;

	private String paymentStatus;

	private String submissionState;

	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	

	@ManyToOne
	@JoinColumn(name = "purposeCode")
	private PaymentPurposeEntity paymentPurpose;

	@ManyToOne
	private CurrencyEntity currency;

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

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getSubmissionState() {
		return submissionState;
	}

	public void setSubmissionState(String submissionState) {
		this.submissionState = submissionState;
	}

}