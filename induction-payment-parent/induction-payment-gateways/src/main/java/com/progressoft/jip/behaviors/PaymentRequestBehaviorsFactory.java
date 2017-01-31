package com.progressoft.jip.behaviors;

import java.util.Collection;

import com.progressoft.jip.gateways.views.PaymentRequestView;

public interface PaymentRequestBehaviorsFactory {
	
	Behavior<PaymentRequestView> loadPaymentRequestById();

	Behavior<Void> deletePaymentRequestById();

	Behavior<Void> insertPaymentRequest();

	Behavior<Collection<PaymentRequestView>> loadPaymentRequests();

	Behavior<Collection<PaymentRequestView>> loadPaymentRequestsByOrderingAccIBAN();

	Behavior<Collection<PaymentRequestView>> loadPaymentRequestsByPaymentDate();

	Behavior<Void> updatePaymentRequest();
}
