package com.progressoft.jip.usecases.validators;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;

public interface PaymentPurposeValidator extends Validator<PaymentPurpose,PurposeValidationException>{
	
	@Override
	void validate(PaymentPurpose paymentPurpose);
}
