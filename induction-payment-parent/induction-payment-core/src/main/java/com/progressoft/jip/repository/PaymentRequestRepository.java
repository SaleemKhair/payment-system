package com.progressoft.jip.repository;

import java.time.LocalDate;
import java.util.Collection;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.repository.exceptions.RepositoryException;

public interface PaymentRequestRepository {

	PaymentRequest loadPaymentRequestById(int id);

	void insertPaymentRequest(PaymentRequest paymentRequest) throws ValidationException;

	Collection<PaymentRequest> loadPaymentRequests();

	void deletePaymentRequestById(int id);

	Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIban(String iban) throws RepositoryException;

	Collection<PaymentRequest> loadPaymentRequestsByPaymentDate(LocalDate now);

	void updatePaymentRequest(PaymentRequest request);

}