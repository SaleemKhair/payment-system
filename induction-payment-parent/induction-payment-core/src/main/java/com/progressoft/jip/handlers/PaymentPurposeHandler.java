package com.progressoft.jip.handlers;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;

@FunctionalInterface
public interface PaymentPurposeHandler {

	void validatePaymentPurpose(PaymentPurpose paymentPurpose) throws PurposeValidationException;

}