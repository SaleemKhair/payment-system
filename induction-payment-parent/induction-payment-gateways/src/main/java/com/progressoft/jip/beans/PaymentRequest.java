package com.progressoft.jip.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("PaymentRequest")
public class PaymentRequest implements Serializable, PaymentRequestView {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	private int id;

	private String orderingAccountIban;
	private String beneficiaryAccountIban;
	private String beneficiaryName;
	private BigDecimal paymentAmount;
	private String currencyCode;
	private String purposeCode;
	private Date paymentDate;
	private String amountInWords;
	private String paymentStatus;
	private String submissionState;

	public PaymentRequest() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getOrderingAccountIban() {
		return orderingAccountIban;
	}

	public void setOrderingAccountIban(String orderingAccountIban) {
		this.orderingAccountIban = orderingAccountIban;
	}

	@Override
	public String getBeneficiaryAccountIban() {
		return beneficiaryAccountIban;
	}

	public void setBeneficiaryAccountIban(String beneficiaryAccountIban) {
		this.beneficiaryAccountIban = beneficiaryAccountIban;
	}

	@Override
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Override
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String getPurposeCode() {
		return purposeCode;
	}

	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}

	@Override
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	@Override
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String getSubmissionState() {
		return submissionState;
	}

	public void setSubmissionState(String submissionState) {
		this.submissionState = submissionState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentRequest other = (PaymentRequest) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
