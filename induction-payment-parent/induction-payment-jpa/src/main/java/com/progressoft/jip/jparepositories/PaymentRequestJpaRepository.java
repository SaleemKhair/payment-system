package com.progressoft.jip.jparepositories;

import java.util.Collection;
import java.util.Date;

import com.progressoft.jip.entities.PaymentRequestEntity;

public interface PaymentRequestJpaRepository {

	void deletePaymentRequestById(int id);

	PaymentRequestEntity loadPaymentRequestById(int id);

	void insertPaymentRequest(PaymentRequestEntity paymentRequest);

	Collection<PaymentRequestEntity> loadPaymentRequests();

	Collection<PaymentRequestEntity> loadPaymentRequestByPaymentDate(Date paymentRequestDate);

	Collection<PaymentRequestEntity> loadPaymentRequestsByOrderingAccountIBAN(String iban);

	void updatePaymentRequest(PaymentRequestEntity paymentRequest);

}