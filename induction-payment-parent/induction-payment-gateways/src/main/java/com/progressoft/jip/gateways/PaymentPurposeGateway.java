package com.progressoft.jip.gateways;

import java.util.Collection;

import com.progressoft.jip.gateways.views.PaymentPurposeView;

public interface PaymentPurposeGateway {

	PaymentPurposeView loadPaymentPurposeByCode(String code);

	void insertPaymentPurpose(PaymentPurposeView paymentPurposeDataStructure);

	Collection<PaymentPurposeView> loadPaymentPurposes();

	void deletePaymentPurposeByCode(String code);

	void updatePaymentPurposeName(PaymentPurposeView paymentPurpose);

}