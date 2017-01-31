package com.progressoft.jip.usecases.validators;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.exceptions.AccountRuleViolationException;
import com.progressoft.jip.handlers.exceptions.PaymentValidationException;

public interface PaymentRequestValidator extends Validator<PaymentRequest,PaymentValidationException> {
	
	@Override
	void validate(PaymentRequest paymentRequest);
	
	default void validatePaymentRequest(PaymentRequest paymentRequest) throws AccountRuleViolationException{
		validate(paymentRequest);
	}

}
