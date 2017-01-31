package com.progressoft.jip.handlers.impl;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.handlers.PaymentPurposeHandler;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;
import com.progressoft.jip.handlers.validators.Validator;

public class PaymentPurposeHandlerImpl implements PaymentPurposeHandler {

	private Validator<PaymentPurpose,PurposeValidationException> validator;
	public PaymentPurposeHandlerImpl(Validator<PaymentPurpose,PurposeValidationException> validator) {
		this.validator = validator;
	}
	@Override
	public void validatePaymentPurpose(PaymentPurpose paymentPurpose) throws PurposeValidationException {
		validator.validate(paymentPurpose);
	}

}
