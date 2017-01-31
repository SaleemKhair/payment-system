package com.progressoft.jip.usecases.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.handlers.PaymentPurposeHandler;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;
import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.usecases.PaymentPurposeUseCases;

public class PaymentPurposeUseCasesImpl implements PaymentPurposeUseCases {

	private PaymentPurposeRepository paymentPurposeRepository;
	private PaymentPurposeHandler paymentPurposeHandler;

	public PaymentPurposeUseCasesImpl(PaymentPurposeRepository paymentPurposeRepository,
			PaymentPurposeHandler paymentPurposeHandler) {
		this.paymentPurposeRepository = paymentPurposeRepository;
		this.paymentPurposeHandler = paymentPurposeHandler;
	}

	@Override
	public PaymentPurposeView getPaymentPurpose(String purposeCode) {
		return paymentPurposeRepository.loadPaymentPurposeByCode(purposeCode);
	}

	@Override
	public Collection<PaymentPurposeView> getAllPaymentPurposes() {
		Collection<PaymentPurpose> paymentPurposes = paymentPurposeRepository.loadPaymentPurposes();
		List<PaymentPurposeView> purposes = new ArrayList<>();
		purposes.addAll(paymentPurposes);
		return purposes;
	}

	@Override
	public void createPaymentPurpose(PaymentPurpose paymentPurpose) throws PurposeValidationException {
		paymentPurposeHandler.validatePaymentPurpose(paymentPurpose);
		paymentPurposeRepository.insertPaymentPurpose(paymentPurpose.getCode(), paymentPurpose.getName());
	}

	@Override
	public void deletePaymentPurpose(String purposeCode) {
		paymentPurposeRepository.loadPaymentPurposeByCode(purposeCode);
		paymentPurposeRepository.deletePaymentPurpose(purposeCode);
	}

	@Override
	public void editPaymentPurpose(PaymentPurpose paymentPurpose) throws PurposeValidationException {
		paymentPurposeHandler.validatePaymentPurpose(paymentPurpose);
		paymentPurposeRepository.updatePaymenPurposeName(paymentPurpose);
	}

}
