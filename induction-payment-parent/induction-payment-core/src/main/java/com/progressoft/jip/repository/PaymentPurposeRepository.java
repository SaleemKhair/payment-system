package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.beans.PaymentPurpose;

public interface PaymentPurposeRepository {

	PaymentPurpose loadPaymentPurposeByCode(String code);

	Collection<PaymentPurpose> loadPaymentPurposes();

	void updatePaymenPurposeName(PaymentPurpose paymentPurpose);

	void insertPaymentPurpose(String code, String name);

	void deletePaymentPurpose(String code);

}