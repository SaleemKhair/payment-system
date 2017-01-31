package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.gateways.PaymentPurposeGateway;
import com.progressoft.jip.gateways.exceptions.PaymentPurposeNotFoundException;
import com.progressoft.jip.repository.PaymentPurposeRepository;

public class PaymentPurposeRepositoryImpl implements PaymentPurposeRepository {

	protected PaymentPurposeGateway paymentPurposeGateway;

	public PaymentPurposeRepositoryImpl(PaymentPurposeGateway paymentPurposeGateway) {
		this.paymentPurposeGateway = paymentPurposeGateway;
	}

	@Override
	public PaymentPurpose loadPaymentPurposeByCode(String code) {
		PaymentPurpose purpose = (PaymentPurpose) paymentPurposeGateway.loadPaymentPurposeByCode(code);
		if (Objects.isNull(purpose))
			throw new PaymentPurposeNotFoundException();
		return purpose;
	}

	@Override
	public Collection<PaymentPurpose> loadPaymentPurposes() {
		ArrayList<PaymentPurpose> collection = new ArrayList<>();
		paymentPurposeGateway.loadPaymentPurposes().stream().forEach(p -> collection.add((PaymentPurpose) p));
		return Collections.unmodifiableCollection(collection);
	}

	@Override
	public void updatePaymenPurposeName(PaymentPurpose paymentPurpose) {
		paymentPurposeGateway.updatePaymentPurposeName(paymentPurpose);
	}

	@Override
	public void insertPaymentPurpose(String code, String name) {
		paymentPurposeGateway.insertPaymentPurpose(new PaymentPurpose(code, name));
	}

	@Override
	public void deletePaymentPurpose(String code) {
		paymentPurposeGateway.deletePaymentPurposeByCode(code);
	}

}
