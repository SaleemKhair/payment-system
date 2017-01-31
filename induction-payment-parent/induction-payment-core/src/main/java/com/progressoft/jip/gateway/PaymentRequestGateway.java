package com.progressoft.jip.gateway;

import java.time.LocalDate;
import java.util.Collection;

import com.progressoft.jip.gateways.views.PaymentRequestView;

public interface PaymentRequestGateway {

	void deletePaymentRequestById(int id);

	PaymentRequestView loadPaymentRequestById(int id);

	void insertPaymentRequest(PaymentRequestView paymentRequestDataStructure);

	Collection<PaymentRequestView> loadPaymentRequests();

	Collection<PaymentRequestView> loadPaymentRequestsByOrderingAccountIban(String iban);

	Collection<PaymentRequestView> loadPaymentRequestsByPaymentDate(LocalDate now);

	void updatePaymentRequest(PaymentRequestView request);

}