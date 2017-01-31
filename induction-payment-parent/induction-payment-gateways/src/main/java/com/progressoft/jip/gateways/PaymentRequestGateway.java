package com.progressoft.jip.gateways;

import java.time.LocalDate;
import java.util.Collection;

import com.progressoft.jip.gateways.views.PaymentRequestView;

public interface PaymentRequestGateway {

    void deletePaymentRequestById(int id);

    PaymentRequestView loadPaymentRequestById(int id);

    void insertPaymentRequest(PaymentRequestView paymentRequest);

    Collection<PaymentRequestView> loadPaymentRequests();

    Collection<PaymentRequestView> loadPaymentRequestsByOrderingAccountIban(String iban);

	Collection<PaymentRequestView> loadPaymentRequestsByPaymentDate(LocalDate paymentDate);

	void updatePaymentRequest(PaymentRequestView request);

}