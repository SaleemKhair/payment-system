package com.progressoft.jip.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the paymentPurpose database table.
 * 
 */
@Entity
@Table(name="paymentPurpose")
@NamedQueries({@NamedQuery(name="PaymentPurposeEntity.findAll", query="SELECT p FROM PaymentPurposeEntity p"),
@NamedQuery(name="PaymentPurposeEntity.findByCode", query="SELECT p FROM PaymentPurposeEntity p where p.code=:code")})
public class PaymentPurposeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String name;

	//bi-directional many-to-one association to PaymentRequestEntity
	@OneToMany(mappedBy="paymentPurpose")
	private List<PaymentRequestEntity> paymentRequests;

	public PaymentPurposeEntity() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PaymentRequestEntity> getPaymentRequests() {
		return this.paymentRequests;
	}

	public void setPaymentRequests(List<PaymentRequestEntity> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}

	public PaymentRequestEntity addPaymentRequest(PaymentRequestEntity paymentRequest) {
		getPaymentRequests().add(paymentRequest);
		paymentRequest.setPaymentPurpose(this);

		return paymentRequest;
	}

	public PaymentRequestEntity removePaymentRequest(PaymentRequestEntity paymentRequest) {
		getPaymentRequests().remove(paymentRequest);
		paymentRequest.setPaymentPurpose(null);

		return paymentRequest;
	}

}