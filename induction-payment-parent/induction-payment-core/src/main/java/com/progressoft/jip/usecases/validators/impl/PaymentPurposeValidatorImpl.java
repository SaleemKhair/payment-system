package com.progressoft.jip.usecases.validators.impl;

import java.util.Objects;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;
import com.progressoft.jip.usecases.exceptions.InvalidPaymentPurposeCodeException;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeCodeException;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeNameException;
import com.progressoft.jip.usecases.validators.Validator;

public class PaymentPurposeValidatorImpl implements Validator<PaymentPurpose,PurposeValidationException> {

	@Override
	public void validate(PaymentPurpose paymentPurpose) throws PurposeValidationException {
		String code = paymentPurpose.getCode();
		String name = paymentPurpose.getName();

		if (Objects.isNull(name)) {
			throw new NullPaymentPurposeNameException();
		}
		if (Objects.isNull(code)) {
			throw new NullPaymentPurposeCodeException();
		}
		if (code.length() == 4) {
			throw new InvalidPaymentPurposeCodeException("Payment Purpose Code should be of length '4'.");
		}
	}

}
