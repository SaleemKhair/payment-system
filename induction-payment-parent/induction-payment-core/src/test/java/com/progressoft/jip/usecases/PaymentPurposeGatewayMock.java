package com.progressoft.jip.usecases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.gateways.PaymentPurposeGateway;
import com.progressoft.jip.gateways.views.PaymentPurposeView;

public class PaymentPurposeGatewayMock implements PaymentPurposeGateway {

	private List<PaymentPurposeView> paymentPurposes;

	public PaymentPurposeGatewayMock() {
		paymentPurposes = new ArrayList<>();
		PaymentPurpose p = new PaymentPurpose();
		p.setCode("SALA");
		p.setName("SALARY");
		paymentPurposes.add(p);
		PaymentPurpose np = new PaymentPurpose();
		np.setCode("NORM");
		np.setName("NORMAL");
		paymentPurposes.add(np);
	}

	@Override
	public PaymentPurpose loadPaymentPurposeByCode(String code) {
		PaymentPurposeView loadedPaymentPurpose = null;
		for (PaymentPurposeView purpose : paymentPurposes) {
			if (purpose.getCode().equals(code))
				loadedPaymentPurpose = purpose;
		}
		return (PaymentPurpose) loadedPaymentPurpose;
	}

	@Override
	public void insertPaymentPurpose(PaymentPurposeView paymentPurpose) {
		paymentPurposes.add(paymentPurpose);
	}

	@Override
	public Collection<PaymentPurposeView> loadPaymentPurposes() {
		return Collections.unmodifiableCollection(paymentPurposes);
	}

	@Override
	public void deletePaymentPurposeByCode(String code) {
		for (int i = 0; i < paymentPurposes.size(); i++) {
			if (paymentPurposes.get(i).getCode().equals(code))
				paymentPurposes.remove(i);
		}
	}

	@Override
	public void updatePaymentPurposeName(PaymentPurposeView paymentPurpose) {
		for (int i = 0; i < paymentPurposes.size(); i++) {
			if (paymentPurposes.get(i).getCode().equals(paymentPurpose.getCode())) {
				PaymentPurpose pp = (PaymentPurpose) paymentPurposes.get(i);
				pp.setName(paymentPurpose.getName());
			}
		}
	}
}
