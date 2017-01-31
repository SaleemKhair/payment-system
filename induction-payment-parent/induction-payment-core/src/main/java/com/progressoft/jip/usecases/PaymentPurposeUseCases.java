package com.progressoft.jip.usecases;

import java.util.Collection;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;

public interface PaymentPurposeUseCases {

	PaymentPurposeView getPaymentPurpose(String purposeCode);

	Collection<PaymentPurposeView> getAllPaymentPurposes();

	void createPaymentPurpose(PaymentPurpose paymentPurpose) throws PurposeValidationException;

	void deletePaymentPurpose(String purposeCode);

	void editPaymentPurpose(PaymentPurpose paymentPurpose) throws PurposeValidationException;

}
